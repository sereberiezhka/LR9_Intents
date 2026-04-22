package com.example.lr9_intents;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Проверка интернета
    public void checkInternetClick(View view) {
        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwi = conMan.getActiveNetworkInfo();

        boolean isConnected = nwi != null && nwi.isConnected();

        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(isConnected ? "Подключение есть!" : "Нет подключения")
                .setMessage(isConnected ? "Добро пожаловать в приложение." : "Разрешите доступ и повторите попытку.")
                .setIcon(isConnected ? R.drawable.img_on : R.drawable.img_off) // Маленькая картинка слева от текста
                .setPositiveButton("ОК", null)
                .show();
    }

    // Метод для задания 2: Звонок
    public void onCallButtonClick(View view) {
        // Берем текст прямо с нажатой кнопки
        Button b = (Button) view;
        String phoneNumber = b.getText().toString();

        // Создаем намерение ACTION_DIAL (открывает набор номера)
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    // Методы для задания 3: Email

    // Кнопка "Очистить"
    public void onClearEmailClick(View view) {
        EditText etTo = findViewById(R.id.etEmailTo);
        EditText etSubject = findViewById(R.id.etEmailSubject);
        EditText etMessage = findViewById(R.id.etEmailMessage);

        etTo.setText("");
        etSubject.setText("");
        etMessage.setText("");
    }

    // Кнопка "Отправить"
    public void onSendEmailClick(View view) {
        EditText etTo = findViewById(R.id.etEmailTo);
        EditText etSubject = findViewById(R.id.etEmailSubject);
        EditText etMessage = findViewById(R.id.etEmailMessage);

        // Создаем намерение ACTION_SEND
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        // Указываем тип данных (обычный текст)
        emailIntent.setType("plain/text");

        // Заполняем поля: Кому, Тема, Текст
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{etTo.getText().toString()});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
        emailIntent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());

        // Запускаем через Chooser (выбор приложения), чтобы не было вылета
        startActivity(Intent.createChooser(emailIntent, "Выберите почтовый клиент:"));
    }

    public void onOpenBrowserClick(View view) {
        Button b = (Button) view;
        String url = "";

        if (b.getText().toString().contains("Google")) {
            url = "https://www.google.com";
        } else {
            url = "https://ru.wikipedia.org";
        }

        Intent intent = new Intent(this, BrowserActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
