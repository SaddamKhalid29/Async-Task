package com.example.finalasynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    /* *********** Steps  ***********
    * Step 1 : Create a subclass and extend it from AsyncTask class
    * Step 2: Implement methods doInbackground in which background operations will be performed in background thread
    * Step 3: Create connection and decode
    * Step 4: Add permissions in Manifest File
    * Step 5: Override onPostExecute Method
    * */
    Button btnImage;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnImage=findViewById(R.id.btnGetImage);
        imageView=findViewById(R.id.imageView);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://uog.edu.pk/uog/upload/events/logo_e.jpg";
                MyAsyncask task=new MyAsyncask();
                task.execute(url);
            }
        });
    }

    public  class  MyAsyncask extends AsyncTask<String,Integer, Bitmap>{

        URL imageUrl=null;
        InputStream inputStream=null;
        Bitmap bitmap=null;

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                imageUrl=new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection) imageUrl.openConnection();
                connection.setDoInput(true);
                connection.connect();
                inputStream=connection.getInputStream();
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                bitmap=BitmapFactory.decodeStream(inputStream,null,options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}