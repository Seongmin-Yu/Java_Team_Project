package ilyeon.ameokja;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ilyeon.ameokja.model.detRetroInter;
import ilyeon.ameokja.model.foodRetrofit;
import ilyeon.ameokja.model.foods;
import ilyeon.ameokja.model.words;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Detail extends AppCompatActivity {



        static String item;
        static final String URL="http://35.200.68.66:2207/";

        Toolbar detailToolBar;
        ImageButton favbut;
        TextView detailtext,lcount,wod;
        ImageView img;
        Bitmap bitmap;

        String wd="";

        int fvn=0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.detail);


            detailToolBar=(Toolbar)findViewById(R.id.detool);
            setSupportActionBar(detailToolBar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.backarrow);

            favbut=(ImageButton)findViewById(R.id.favoricon_empty);
            detailtext=(TextView)findViewById(R.id.detailtext);
            lcount=(TextView)findViewById(R.id.lcount);
            img=(ImageView)findViewById(R.id.imageV);
            wod=(TextView)findViewById(R.id.keyword);

            //













            Thread m = new Thread() {
                @Override
                public void run() {
                    try {
                        Retrofit retrofit=new Retrofit.Builder()
                                .baseUrl(URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();


                        detRetroInter service=retrofit.create(detRetroInter.class);
                        Call<foodRetrofit> call=service.getDetail("2");
                        call.enqueue(new Callback<foodRetrofit>() {
                            @Override
                            public void onResponse(Call<foodRetrofit> call, Response<foodRetrofit> response) {
                                foodRetrofit repo=response.body();


                                foods food=new foods();

                                food.setContext(repo.getFoodlist().get(0).getContext());
                                food.setFoodname(repo.getFoodlist().get(0).getFoodname());
                                food.setLike(repo.getFoodlist().get(0).getLike());

                                for(int i=0;i<repo.getFoodword().size();i++){
                                    words word=new words();
                                    wd+="#"+(repo.getFoodword().get(i).getWord())+" ";
                                }

                                detailtext.setText(food.getContext());
                                getSupportActionBar().setTitle(food.getFoodname());
                                lcount.setText(food.getLike());
                                wod.setText(wd);

                                item=food.getFoodname();


                            }

                            @Override
                            public void onFailure(Call<foodRetrofit> call, Throwable t) {

                            }
                        });

                        java.net.URL url = new URL("https://s3.ap-northeast-2.amazonaws.com/amugja/"+item+".jpg");


                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            m.start();

            try {
                m.join();
                img.setImageBitmap(bitmap);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }




            favbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (fvn == 0) {
                        fvn++;
                        favbut.setBackgroundResource(R.drawable.favorite_icon);

                    } else if (fvn == 1) {
                        fvn--;
                        favbut.setBackgroundResource(R.drawable.favorite_icon_empty);

                    }
                }

            });

        }



        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()){
                case android.R.id.home:{
                    finish();
                    //onBackPressed();
                    return true;
                }
            }
            return super.onOptionsItemSelected(item);
        }





    }
