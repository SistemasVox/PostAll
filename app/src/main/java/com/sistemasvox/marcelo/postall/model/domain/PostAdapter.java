package com.sistemasvox.marcelo.postall.model.domain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sistemasvox.marcelo.postall.R;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    Context ctx;
    List<Post> lista;

    public PostAdapter(Context ctx, List<Post> lista) {
        this.ctx = ctx;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Passo 1 Criar o Objeto Post
        Post post = lista.get(position);

        //Passo 2 Criar Linha (Lista de Layouts)
        View linha = LayoutInflater.from(ctx).inflate(R.layout.linha,null);

        //Passo 3
        TextView id = (TextView) linha.findViewById(R.id.id);
        TextView titulo = (TextView) linha.findViewById(R.id.titulo);

        id.setText("ID: " + post.getId());
        titulo.setText("Título: " + post.getTitle());

        return linha;
    }
}
