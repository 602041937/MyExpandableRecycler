package com.hpd.myexpandablerecycler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);

        List<Tree<?>> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Tree<String> tree = new Tree<>(String.format("第%02d组", i));
            for (int j = 0; j < 10; j++) {
                Tree<String> child = new Tree<>(String.format("第%02d组 第%02d条", i, j));
                tree.addChild(child);
                for (int z = 0; z < 10; z++) {
                    Tree<String> childOfChild =
                            new Tree<>(String.format("第%02d组 第%02d条 第%02d项", i, j, z));
                    child.addChild(childOfChild);
                }
            }
            list.add(tree);
        }
        recycler.setAdapter(new TreeAdapter(this, list));
    }
}
