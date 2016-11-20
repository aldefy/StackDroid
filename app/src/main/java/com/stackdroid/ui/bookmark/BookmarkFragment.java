package com.stackdroid.ui.bookmark;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stackdroid.R;
import com.stackdroid.api.models.QItems;
import com.stackdroid.ui.QuestionsRVAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by aditlal on 17/04/16.
 */
public class BookmarkFragment extends Fragment  {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    List<QItems> adapterItems;
    QuestionsRVAdapter adapter;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);
        ButterKnife.bind(this, rootView);
        initViews();
        getQnsFromDb();
        return rootView;
    }

    private void initViews() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching list");
        adapterItems = new ArrayList<>();
        adapter = new QuestionsRVAdapter(getActivity(), adapterItems, true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    private void getQnsFromDb() {
    //TODO call the presenter
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
