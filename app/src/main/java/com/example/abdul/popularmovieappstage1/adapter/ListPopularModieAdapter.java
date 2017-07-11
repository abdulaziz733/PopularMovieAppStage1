package com.example.abdul.popularmovieappstage1.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdul.popularmovieappstage1.R;
import com.example.abdul.popularmovieappstage1.interfaces.OnLoadMoreListener;
import com.example.abdul.popularmovieappstage1.model.Movie;
import com.example.abdul.popularmovieappstage1.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by abdul on 7/9/2017.
 */

public class ListPopularModieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private boolean isLoading;
    private List<Movie> movieList;
    private Context context;
    private ListPopularModieAdapterListener listener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private OnLoadMoreListener onLoadMoreListener;

    public ListPopularModieAdapter(RecyclerView recyclerView, List<Movie> movieList, final Context context, ListPopularModieAdapterListener listener) {
        this.movieList = movieList;
        this.context = context;
        this.listener = listener;

        final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = gridLayoutManager.getItemCount();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.popular_movie_item, parent, false);

            return new DataViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);

            return new ListPopularModieAdapter.LoadingViewHolder(itemView);
        }

        return null;
    }


    @Override
    public int getItemViewType(int position) {
        return movieList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DataViewHolder) {
            Movie movie = movieList.get(position);
            DataViewHolder dataViewHolder = (DataViewHolder) holder;
            dataViewHolder.movieTitle.setText(movie.getTitle());
            dataViewHolder.moviePopularity.setText("Popularity: " + movie.getPopularity());

            Picasso.with(context)
                    .load(Constant.BASE_IMAGE_URL + movie.getPosterPath())
                    .placeholder(R.drawable.movie_placeholder)
                    .error(R.drawable.error_image)
                    .into(dataViewHolder.moviePoster);

            dataViewHolder.viewMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onRowClicked(position);
                }
            });

        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    public class DataViewHolder extends RecyclerView.ViewHolder {

        private ImageView moviePoster;
        private TextView movieTitle, moviePopularity;
        private RelativeLayout viewMovie;

        public DataViewHolder(View itemView) {
            super(itemView);
            moviePoster = (ImageView) itemView.findViewById(R.id.img_movie);
            movieTitle = (TextView) itemView.findViewById(R.id.title_movie);
            moviePopularity = (TextView) itemView.findViewById(R.id.popularity_movie);
            viewMovie = (RelativeLayout) itemView.findViewById(R.id.view_item_movie);


        }
    }

    public interface ListPopularModieAdapterListener {

        void onRowClicked(int position);

    }

}
