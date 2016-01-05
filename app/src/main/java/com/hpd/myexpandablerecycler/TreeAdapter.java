package com.hpd.myexpandablerecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.List;

/**
 * Created by jash
 * Date: 16-1-5
 * Time: 上午11:09
 */
public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Tree<?>> list;
    private RecyclerView recyclerView;

    public TreeAdapter(Context context, List<Tree<?>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TreeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.child_item, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.child_of_child_item, parent, false);
                break;
            default:
                view = LayoutInflater.from(context).inflate(R.layout.child_of_child_item, parent, false);
                break;
        }
        view.setOnClickListener(this);
        return new TreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TreeViewHolder holder, int position) {
        Tree<?> tree = list.get(position);
        switch (tree.getLevel()) {
            case 0:
                holder.group_text.setText((String) tree.getData());
                holder.expand.setChecked(tree.isExpand());
                break;
            case 1:
                holder.child_expand.setChecked(tree.isExpand());
                holder.child_text.setText((String) tree.getData());
                break;
            case 2:
                holder.child_of_child_text.setText((String) tree.getData());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //设置多布局，根据level
    @Override
    public int getItemViewType(int position) {

        return list.get(position).getLevel();
    }

    //得到recyclerView
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        Tree<?> tree = list.get(position);
        int level = tree.getLevel();
        if (tree.isExpandable()) {

            if (tree.isExpand()) {
                if (level == 0) {
                    List<Tree<?>> list = tree.getChildren();
                    for (int i = list.size() - 1; i >= 0; i--) {
                        if (list.get(i).isExpand()) {
                            removeAll(position + 1 + i + 1, list.get(i).getChildren());
                        }
                    }
                    removeAll(position + 1, tree.getChildren());
                } else if (level == 1) {
                    removeAll(position + 1, tree.getChildren());
                }
            } else {
                addAll(position + 1, tree.getChildren());
                if (level == 0) {
                    position = position + 1;
                    List<Tree<?>> list2 = tree.getChildren();
                    int count = 0;
                    for (int i = 0; i < list2.size(); i++) {
                        if (list2.get(i).isExpand()) {

                            addAll(position + 1, list2.get(i).getChildren());
                            count = list2.get(i).getChildren().size();
                            position = position + count + 1;
                        } else {
                            position += 1;
                        }

                    }
                }


            }

            tree.setExpand(!tree.isExpand());
            notifyItemChanged(position);
        } else {
            Toast.makeText(v.getContext(), (String) tree.getData(), Toast.LENGTH_SHORT).show();
        }
    }

    public void addAll(int position, Collection<? extends Tree<?>> collection) {
        list.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }

    public void removeAll(int position, Collection<? extends Tree<?>> collection) {
        list.removeAll(collection);
        notifyItemRangeRemoved(position, collection.size());
    }

    //1.定义一个holder
    public static class TreeViewHolder extends RecyclerView.ViewHolder {

        private CheckBox expand;
        private CheckBox child_expand;
        private TextView group_text;
        private TextView child_text;
        private TextView child_of_child_text;

        public TreeViewHolder(View itemView) {
            super(itemView);
            expand = ((CheckBox) itemView.findViewById(R.id.expand));
            child_expand = (CheckBox) itemView.findViewById(R.id.child_expand);
            group_text = ((TextView) itemView.findViewById(R.id.group_text));
            child_text = ((TextView) itemView.findViewById(R.id.child_text));
            child_of_child_text = (TextView) itemView.findViewById(R.id.child_child_text);
        }
    }
}
