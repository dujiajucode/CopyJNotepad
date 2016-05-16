package net.dujiaju.pnotepad.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.dujiaju.pnotepad.R;

/**
 * Created by lilujia on 16/5/15.
 */
public class ArticleListFragment extends Fragment {

    public static final String ARTICLE_LIST_FRAGMENT_FOLDER = "ARTICLE_LIST_FRAGMENT_FOLDER";

    private String mTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getString(ARTICLE_LIST_FRAGMENT_FOLDER);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);


        return view;
    }
}
