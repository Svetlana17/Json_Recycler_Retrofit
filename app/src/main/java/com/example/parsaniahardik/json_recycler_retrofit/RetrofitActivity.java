package com.example.parsaniahardik.json_recycler_retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitActivity extends AppCompatActivity {


   private  Adapter adapter;
   private RecyclerView r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        r = findViewById(R.id.rec);

        fetchJSON();
        
    }

    private void fetchJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Recycle.JSONURL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        Recycle api = retrofit.create(Recycle.class);
        Call<String> call = api.getString();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {  // если запрос к серверу успешен
                    if (response.body() != null) { // тело ответа не пусто
                        Log.i("onSuccess", response.body().toString());
                        //Записываем ответ сервера в переменную jsonresponse
                        String jsonresponse = response.body().toString();
                        writeRecycler(jsonresponse);

                    } else {
                        Log.i("onEmptyResponse", "Returned empty response");//Toast.makeText(getContext(),"Nothing returned",Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void writeRecycler(String jsonresponse) {
        try {

            // Создаем новый Json обеъект, в который будем записывать ответ сервера
            JSONObject obj = new JSONObject(jsonresponse);
            // Проверяем, если  Json обеъект - obj
            if(obj.optString("status").equals("true")){
                 // Создаем новый ArrayList - для отображения результатов

                ArrayList<Model> modelRecyclerArrayList1 = new ArrayList<>();
                // Создаем массив json объектов
                JSONArray dataArray  = obj.getJSONArray("data");
                // Считываем данные из массива
                for (int i = 0; i < dataArray.length(); i++) {
                    // Указываем какие данные необходимо считать
                    Model model = new Model();
                    // Получаем JSONObject
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    // Устанавливаем полученные элементы
                    // set - установить
                    model.setImgURL(dataobj.getString("imgURL"));
                    model.setName(dataobj.getString("name"));
                    model.setCountry(dataobj.getString("country"));
                    model.setCity(dataobj.getString("city"));
                    // Добавялем в список нашу модель
                    modelRecyclerArrayList1.add(model);

                }
                // Создаем экземпляр класса Adapter
                adapter = new Adapter(this,modelRecyclerArrayList1);
                // Добавляем в RecycleView aдаптер
                r.setAdapter(adapter);
                // Устанавливаем LinearLayoutManager качестве менеджера макета для достижения макета списка
                r.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

            }
            else {
                Toast.makeText(RetrofitActivity.this, obj.optString("message") + "", Toast.LENGTH_SHORT).show();
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



