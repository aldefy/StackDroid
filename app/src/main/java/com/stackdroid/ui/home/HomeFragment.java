package com.stackdroid.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.stackdroid.R;
import com.stackdroid.api.models.QItems;
import com.stackdroid.ui.QuestionsRVAdapter;
import com.stackdroid.utils.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by aditlal on 16/04/16.
 */
public class HomeFragment extends Fragment {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindString(R.string.sort_activity_title)
    String sort_activity_title;
    @BindString(R.string.sort_creation_title)
    String sort_creation_title;
    @BindString(R.string.sort_votes_title)
    String sort_votes_title;
    @BindString(R.string.sort_relevance_title)
    String sort_relevance_title;
    @BindString(R.string.sort_activity_value)
    String sort_activity;
    @BindString(R.string.sort_creation_value)
    String sort_creation;
    @BindString(R.string.sort_votes_value)
    String sort_votes;
    @BindString(R.string.sort_relevance)
    String sort_relevance;
    String sortString;

    private List<String> tags;
    private List<QItems> adapterItems;
    private QuestionsRVAdapter adapter;
    private ArrayAdapter<String> tagAdapter;
    private SearchView searchView;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questions, container, false);
        ButterKnife.bind(this, rootView);
        initViews();

        fetchQns(1, sort_activity);
        return rootView;
    }

    private void initViews() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching list");
        adapterItems = new ArrayList<>();
        tags = new ArrayList<>();
        sortString = sort_activity;
        adapter = new QuestionsRVAdapter(getActivity(), adapterItems, false);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);
        EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(llm) {
            @Override
            public void onLoadMore(int current_page) {
                Timber.tag("EndlessScroll").d(current_page + "");
                fetchQns(current_page, sortString);
            }
        };
        recyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
    }

    public void changeSort() {

    }



    private void fetchTags() {
        //TODO call the preseneter

    }

    private void fetchQns(int page, String sort) {
      //TODO call the preseneter
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if (!searchView.isIconified())
                    searchView.onActionViewCollapsed();
                return false;
            }
            default:
                return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
