package com.example.conversordeunidades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText inputValue;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialização dos componentes de UI
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputValue = findViewById(R.id.inputValue);
        Button convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Lista de unidades de medida
        String[] units = {"Centímetros", "Metros", "Quilômetros", "Milhas"};

        // Configuração dos Spinners com as unidades
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        // Ação do botão de conversão
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromUnit = spinnerFrom.getSelectedItem().toString();
                String toUnit = spinnerTo.getSelectedItem().toString();
                String inputText = inputValue.getText().toString();

                // Verifica se o valor de entrada é válido
                if (!inputText.isEmpty()) {
                    try {
                        double value = Double.parseDouble(inputText);
                        double result = convertUnits(value, fromUnit, toUnit);
                        resultTextView.setText("Resultado: " + result + " " + toUnit);
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Erro: Digite um valor numérico", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Digite um valor válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Função de conversão de unidades
    private double convertUnits(double value, String fromUnit, String toUnit) {
        double valueInMeters;

        // Converter para metros como unidade intermediária
        switch (fromUnit) {
            case "Centímetros":
                valueInMeters = value / 100;
                break;
            case "Metros":
                valueInMeters = value;
                break;
            case "Quilômetros":
                valueInMeters = value * 1000;
                break;
            case "Milhas":
                valueInMeters = value * 1609.34;
                break;
            default:
                valueInMeters = value;
                break;
        }

        // Converter de metros para a unidade de destino
        switch (toUnit) {
            case "Centímetros":
                return valueInMeters * 100;
            case "Metros":
                return valueInMeters;
            case "Quilômetros":
                return valueInMeters / 1000;
            case "Milhas":
                return valueInMeters / 1609.34;
            default:
                return valueInMeters;
        }
    }
}
