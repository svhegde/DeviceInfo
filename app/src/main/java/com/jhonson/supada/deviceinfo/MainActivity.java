/**
 * MainActivity.java is the main activity class which implements  "swipe to delete" on a recycler view with no 3rd party Library and
 *  drawing on empty space while items are animating with an "undo" option.It also includes REST API implementation using Retrofit .
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import okhttp3.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mRecyclerView;
    List<Device> deviceList;
    //DatabaseHelper myDb;
    Boolean callApi = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setUpRecyclerView();
        ImageButton button = (ImageButton) findViewById(R.id.imageButton);
        button.setOnClickListener(this);
        //myDb = new DatabaseHelper(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddDevice.class);
        int ListSize = 0;
        if(deviceList != null) {
            ListSize = deviceList.size();
        }
        intent.putExtra("count", ListSize);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_item_undo_checkbox) {
            item.setChecked(!item.isChecked());
            ((TestAdapter) mRecyclerView.getAdapter()).setUndoOn(item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new TestAdapter());
        mRecyclerView.setHasFixedSize(true);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
    }

    /**
     * This is the standard support library way of implementing "swipe to delete" feature.
     */
    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            // Cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.rgb(9,46,32));
                xMark = ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_clear_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) MainActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }


            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                TestAdapter testAdapter = (TestAdapter) recyclerView.getAdapter();
                if (testAdapter.isUndoOn() && testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();

                TestAdapter adapter = (TestAdapter) mRecyclerView.getAdapter();

                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition);
                } else {
                    adapter.remove(swipedPosition);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;


                if (viewHolder.getAdapterPosition() == -1) {

                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw the background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private void setUpAnimationDecoratorHelper() {
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.rgb(9,46,32));
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {


                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    int left = 0;
                    int right = parent.getWidth();

                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    /**
     * RecyclerView adapter enabling undo on a swiped away item.
     */
    class TestAdapter extends RecyclerView.Adapter {

        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec

        List<String> items;
        List<String> itemsPendingRemoval;
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables
        HashMap<String, Runnable> pendingRunnables = new HashMap<>(); // map of items to pending runnables, so we can cancel a removal if need be

        public TestAdapter() {
            items = new ArrayList<>();
            itemsPendingRemoval = new ArrayList<>();
            //myDb = new DatabaseHelper(getApplicationContext());
            //final Device dbDevice = myDb.getAllData();

                DeviceAPI.Factory.getInstance().getDevices().enqueue(new Callback<List<Device>>() {
                    @Override
                    public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                        if (response.isSuccessful()) {
                            deviceList = response.body();
                            for (int i = 0; i < deviceList.size(); i++) {

                                if (deviceList.get(i).isIsCheckedOut()) {
                                    items.add(deviceList.get(i).getDevice() + " - " + deviceList.get(i).getOs() + "-" + "Checked out by " + deviceList.get(i).getLastCheckedOutBy());

                                } else {
                                    items.add(deviceList.get(i).getDevice() + " - " + deviceList.get(i).getOs() + "Available");
                                }
                                Log.d("API Data", deviceList.get(i).getDevice());
                                /*myDb.insertData(Integer.toString(deviceList.get(i).getId()), deviceList.get(i).getDevice(),
                                        deviceList.get(i).getOs(), deviceList.get(i).getManufacturer(),
                                        deviceList.get(i).getLastCheckedOutDate(), deviceList.get(i).getLastCheckedOutBy(),
                                        String.valueOf(deviceList.get(i).isIsCheckedOut())
                                );*/
                                //Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                                notifyDataSetChanged();
                            }
                        } else {
                            int statusCode = response.code();

                            // handle request errors
                            ResponseBody errorBody = response.errorBody();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Device>> call, Throwable t) {
                        Log.e("API ERROR", t.getMessage());
                    }
                });
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(parent);

        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TestViewHolder viewHolder = (TestViewHolder) holder;
            final String item = items.get(position);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Device selectedDevice = deviceList.get(position);
                    Intent deviceIntent = new Intent(getApplicationContext(), DeviceDetails.class);
                    deviceIntent.putExtra("id", selectedDevice.getId());
                    deviceIntent.putExtra("device", selectedDevice.getDevice());
                    deviceIntent.putExtra("os",selectedDevice.getOs());
                    deviceIntent.putExtra("manufacturer",selectedDevice.getManufacturer());
                    deviceIntent.putExtra("user", selectedDevice.getLastCheckedOutBy());
                    deviceIntent.putExtra("lastCheckOut", selectedDevice.getLastCheckedOutDate());
                    deviceIntent.putExtra("isCheckedOut", selectedDevice.isIsCheckedOut());
                    startActivity(deviceIntent);


                }
            });

            if (itemsPendingRemoval.contains(item)) {
                // we need to show the "undo" state of the row
                viewHolder.itemView.setBackgroundColor(Color.rgb(9,46,32));
                viewHolder.titleTextView.setVisibility(View.GONE);
                viewHolder.undoButton.setVisibility(View.VISIBLE);
                viewHolder.undoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // user wants to undo the removal, let's cancel the pending task
                        Runnable pendingRemovalRunnable = pendingRunnables.get(item);
                        pendingRunnables.remove(item);
                        if (pendingRemovalRunnable != null)
                            handler.removeCallbacks(pendingRemovalRunnable);
                        itemsPendingRemoval.remove(item);
                        // this will rebind the row in "normal" state
                        notifyItemChanged(items.indexOf(item));
                    }
                });
            } else {
                // we need to show the "normal" state
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
                viewHolder.titleTextView.setVisibility(View.VISIBLE);
                viewHolder.titleTextView.setText(item);
                viewHolder.undoButton.setVisibility(View.GONE);
                viewHolder.undoButton.setOnClickListener(null);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }



        public void setUndoOn(boolean undoOn) {
            this.undoOn = undoOn;
        }

        public boolean isUndoOn() {
            return undoOn;
        }

        public void pendingRemoval(int position) {
            final String item = items.get(position);
            if (!itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.add(item);
                // this will redraw row in "undo" state
                notifyItemChanged(position);
                // let's create, store and post a runnable to remove the item
                Runnable pendingRemovalRunnable = new Runnable() {
                    @Override
                    public void run() {
                        remove(items.indexOf(item));
                    }
                };
                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
                pendingRunnables.put(item, pendingRemovalRunnable);
            }
        }

        public void remove(int position) {
            String item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            String item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */
    static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        Button undoButton;

        public TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_view, parent, false));
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            undoButton = (Button) itemView.findViewById(R.id.undo_button);
        }

    }



}
