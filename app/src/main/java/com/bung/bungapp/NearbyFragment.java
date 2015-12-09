package com.bung.bungapp;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bung.bungapp.core.ApiCaller;
import com.bung.bungapp.core.Callback;
import com.bung.bungapp.model.Party;
import com.bung.bungapp.util.Async;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;

public class NearbyFragment extends Fragment implements OnMapReadyCallback, SearchBox.MenuListener, SearchBox.SearchListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private SearchBox mSearchBox;
    private MapFragment mMapFragment;
    private ListView mListView;
    private List<Party> mPartyList;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NearbyFragment.
     */
    public static NearbyFragment newInstance(Bundle args) {
        NearbyFragment fragment = new NearbyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public NearbyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        Async.main(() -> reloadParties(), 500);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nearby, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initSearchBox();
        initMapFragment();
        initListView();
        initNearbyChickens();
        reloadParties();
    }

    private void reloadParties() {
        ApiCaller.getParties(new Callback<List<Party>>() {
            @Override
            public void onSuccess(List<Party> result) {
                if (result != null) {
                    mPartyList = result;
                    mMapFragment.setParties(result);
                    mListView.deferNotifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {

            }
        });
    }


    private void initNearbyChickens() {
        mMapFragment.initSearch();
    }

    private void initMapFragment() {
        mMapFragment = MapFragment.newInstance(new Bundle());
        getFragmentManager().beginTransaction()
                .replace(R.id.main_layout, mMapFragment)
                .commit();
    }

    private void initListView() {
        mListView = (ListView) getView().findViewById(R.id.listview);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Party party = mPartyList.get(position);
            if(party != null) {
                mMapFragment.focusParty(party);
            }

        });
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mPartyList != null ? mPartyList.size() : 0;
            }

            @Override
            public Party getItem(int position) {
                if (mPartyList != null) {
                    return mPartyList.get(position);
                }
                return null;
            }

            @Override
            public long getItemId(int position) {
                Party item = getItem(position);
                if (item != null) {
                    return item.hashCode();
                }
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = View.inflate(getActivity(), R.layout.row_recruiting_party, null);
                }
                ImageView profileIcon = (ImageView) convertView.findViewById(R.id.profile_icon);
                TextView location = (TextView) convertView.findViewById(R.id.location);
                TextView count = (TextView) convertView.findViewById(R.id.participant_count);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                TextView owner = (TextView) convertView.findViewById(R.id.owner);

                Party item = getItem(position);
                if (item != null) {
                    title.setText(item.getName());
                    location.setText(item.getPlace_name());
                    owner.setText(item.getCreator().getName());
                    count.setText(String.valueOf(item.getNumber_of_participants()) + "명");
                }
                return convertView;
            }
        });
    }

    private ViewGroup getToolbarLayer() {
        try {
            //noinspection ConstantConditions
            return (ViewGroup) getView().findViewById(R.id.app_bar_layout);
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void initSearchBox() {
        mSearchBox = new SearchBox(getActivity());
        ViewGroup toolbarLayer = getToolbarLayer();
        if (toolbarLayer != null) {
            toolbarLayer.removeAllViewsInLayout();
            toolbarLayer.addView(mSearchBox,
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
            mSearchBox.setVisibility(View.GONE);
        }
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        if (toolbar != null) {
            mSearchBox.setHint("Search");
            mSearchBox.setMenuListener(this);
            mSearchBox.setSearchListener(this);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mSearchBox.setElevation(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
        }

        reloadSearchSuggestion();
    }

    public void reloadSearchSuggestion() {
        if (mSearchBox != null) {
            ArrayList<SearchResult> searchResults = new ArrayList<>();
            mSearchBox.setSearchables(searchResults);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onMenuClick() {

    }

    @Override
    public void onSearchOpened() {

    }

    @Override
    public void onSearchCleared() {

    }

    @Override
    public void onSearchClosed() {

    }

    @Override
    public void onSearchTermChanged() {

    }

    @Override
    public void onSearch(SearchResult result) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.nearby, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                mSearchBox.revealFromMenuItem(R.id.action_search, getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
