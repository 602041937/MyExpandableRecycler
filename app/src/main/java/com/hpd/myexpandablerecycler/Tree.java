package com.hpd.myexpandablerecycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jash
 * Date: 16-1-5
 * Time: 上午10:50
 */
public class Tree<T> {
    private T data;
    private int level = 0;
    private List<Tree<?>> children;
    private boolean expand = false;

    public Tree(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Tree<?>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<?>> children) {
        this.children = children;
    }
    public boolean isExpandable(){
        return children != null && !children.isEmpty();
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
    public void addChild(Tree<?> child){
        if (children == null) {
            children = new ArrayList<>();
        }
        child.setLevel(level + 1);
        children.add(child);
    }
}
