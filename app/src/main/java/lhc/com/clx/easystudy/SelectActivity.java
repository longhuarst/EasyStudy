package lhc.com.clx.easystudy;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.SimpleItemTouchHelperCallback;
import com.marshalchen.ultimaterecyclerview.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    UltimateRecyclerView recyclerView = null;
    UltimateViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);


        recyclerView = findViewById(R.id.recyclerView);

        adapter = new UltimateViewAdapter() {
            @Override
            public RecyclerView.ViewHolder newFooterHolder(View view) {
                return null;
            }

            @Override
            public RecyclerView.ViewHolder newHeaderHolder(View view) {
                return null;
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
                return null;
            }

            @Override
            public int getAdapterItemCount() {
                return 0;
            }

            @Override
            public long generateHeaderId(int position) {
                return 0;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
                return null;
            }

            @Override
            public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        };


        List<String> list = new ArrayList<>();
        list.add("1234");
        list.add("5678");

        //adapter.insertInternal(list,null);





        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        


        StickyRecyclerHeadersDecoration stickyRecyclerHeadersDecoration = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(stickyRecyclerHeadersDecoration);


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter){

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return super.getMovementFlags(recyclerView, viewHolder);
            }


            @Override
            public boolean isItemViewSwipeEnabled() {
                return super.isItemViewSwipeEnabled();
            }


            @Override
            public boolean isLongPressDragEnabled() {
                return super.isLongPressDragEnabled();
            }


        };


        final  ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView.mRecyclerView);
        //recyclerView.setParallaxHeader(head);
        recyclerView.enableDefaultSwipeRefresh(true);
        recyclerView.reenableLoadmore();
        recyclerView.setDefaultOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setRefreshing(false);
            }
        });











    }
}
