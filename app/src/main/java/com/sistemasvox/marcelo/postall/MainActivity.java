package com.sistemasvox.marcelo.postall;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sistemasvox.marcelo.postall.model.domain.ApiEndpoint;
import com.sistemasvox.marcelo.postall.model.domain.Post;
import com.sistemasvox.marcelo.postall.model.domain.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    final Handler handler = new Handler();
    private static final String TAG = "teste";
    private List<Post> posts;
    private ListView listView;
    private PostAdapter adapter;
    private TextView msg, msgID;
    private boolean parar = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        posts = new ArrayList<Post>();


        Log.i(TAG, "Lista Tamanho antes: [" + posts.size()+"].");

        buscarTodosPosts();

        Log.i(TAG, "Lista Tamanho apos : [" + posts.size()+"].");


        listView.setAdapter(new PostAdapter(this, posts));
        setContentView(listView);
    }

    private void buscarTodosPosts() {

        /*
        int cont = 1;
        while (parar == false){
            construirJSON(cont);
            cont ++;
        }*/
        //posts.add(new Post("0", "0", "antes de consultar JSON", ""));
        construirJSON(1);
        Log.i(TAG, "Lista Tamanho apos while : [" + posts.size()+"].");

        /*
        construirJSON(1);
        construirJSON(2);
        construirJSON(3);*/
        /*
        posts.add(new Post("1", "1", "descricao do um", ""));
        posts.add(new Post("2", "2", "descricao do dois", ""));
        posts.add(new Post("3", "3", "descricao do tres", ""));*/
    }


    private void construirJSON(int n) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ApiEndpoint apiService = retrofit.create(ApiEndpoint.class);
        Call<Post> call = apiService.obeterPost(n);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                int statusCode = response.code();
                if (statusCode != 404){
                    Post post = response.body();
                    posts.add(new Post(post.getUserId(), post.getId(), post.getTitle(), post.getBody()));
                    Log.i(TAG, "Entrou no Post: [" + post.getTitle()+"].");
                    //Log.i(TAG, "Lista Size: [" + posts.size()+"].");
                }else{
                    parar = true;
                }

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //mensagem.setText(t.toString());
            }
        });
    }
}
