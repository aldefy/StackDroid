package com.stackdroid.ui.home;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.SearchView.SearchAutoComplete;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.stackdroid.R;
import com.stackdroid.api.ApiGenerator;
import com.stackdroid.api.StackOverflowApi;
import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.TagItem;
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
public class HomeFragment extends Fragment implements OnQueryTextListener, HomeView {


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
    private HomeApiService homeApiService;
    private StackOverflowApi apiInterface;
    private HomePresenter presenter;
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
        apiInterface = ApiGenerator.createService(StackOverflowApi.class);
        homeApiService = new HomeApiService(apiInterface);
        presenter = new HomePresenter(homeApiService, this);
        fetchTags();
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
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_dialog_option, null);
        ((RadioButton) dialogView.findViewById(R.id.unit1)).setText(sort_activity_title);
        ((RadioButton) dialogView.findViewById(R.id.unit2)).setText(sort_creation_title);
        ((RadioButton) dialogView.findViewById(R.id.unit3)).setText(sort_votes_title);
        ((RadioButton) dialogView.findViewById(R.id.unit4)).setText(sort_relevance_title);

        switch (sortString) {
            case "activity":
                ((RadioButton) dialogView.findViewById(R.id.unit1)).setChecked(true);
                break;
            case "creation":
                ((RadioButton) dialogView.findViewById(R.id.unit2)).setChecked(true);
                break;
            case "votes":
                ((RadioButton) dialogView.findViewById(R.id.unit3)).setChecked(true);
                break;
            case "relevance":
                ((RadioButton) dialogView.findViewById(R.id.unit4)).setChecked(true);
                break;
            default:
                ((RadioButton) dialogView.findViewById(R.id.unit1)).setChecked(true);
                break;

        }

        MaterialStyledDialog dialog = new MaterialStyledDialog(getActivity())
                .setTitle("Choose sort")
                .setCustomView(dialogView)
                .setHeaderColor(R.color.accent)
                .setScrollable(true)
                .withDialogAnimation(true, Duration.NORMAL)
                .setCancelable(true)
                .setNegative("Cancel", (dialog1, which) -> dialog1.cancel())
                .setPositive("Update", (dialog1, which) -> calSort(dialog1, (RadioGroup) dialogView.findViewById(R.id.unitGroup)))
                .setDescription("Select the sorting")
                .build();
        dialog.show();
    }

    private void calSort(MaterialDialog dialog1, RadioGroup radioGroup) {
        dialog1.dismiss();
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.unit1:
                fetchQns(1, sort_activity);
                break;
            case R.id.unit2:
                fetchQns(1, sort_creation);
                break;
            case R.id.unit3:
                fetchQns(1, sort_votes);
                break;
            case R.id.unit4:
                fetchQns(1, sort_relevance);
                break;
        }
    }

    private void fetchTags() {
        presenter.getTags();
    }

    private void fetchQns(int page, String sort) {
        if (page == 1) {
            adapterItems.clear();
            sortString = sort;
        }
        presenter.getQuestions(sort, page);
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
        SearchAutoComplete searchAutoComplete = (SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setTextColor(Color.WHITE);
        tagAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.layout_auto_suggest_item, tags);
        searchAutoComplete.setAdapter(tagAdapter);

        getActivity();
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchAutoComplete.setOnItemClickListener((parent, view, position, id) -> {
            String searchString = (String) parent.getItemAtPosition(position);
            searchAutoComplete.setText("" + searchString);
        });
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(() -> {
            adapter.getFilter().filter("");
            return false;
        });
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
    public boolean onQueryTextChange(String query) {
        Timber.tag("Filtering").d(query + " " + tagAdapter.getCount());
        adapter.getFilter().filter(query);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public void questionsLoaded(List<QItems> qItemses) {
        adapterItems.addAll(qItemses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void tagsLoaded(List<TagItem> tagList) {
        for (TagItem tag : tagList)
            tags.add(tag.getName());
        if (tagAdapter != null)
            tagAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

    @Override
    public void onFailure(Throwable error) {
        Timber.tag("Error").e( error.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onStop();
    }
}
