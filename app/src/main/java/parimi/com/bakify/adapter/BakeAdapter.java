package parimi.com.bakify.adapter;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import parimi.com.bakify.R;
import parimi.com.bakify.model.BakeReceipe;

public class BakeAdapter extends RecyclerView.Adapter<BakeAdapter.ViewHolder> {
    private List<BakeReceipe> mDataset;
    private final OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(BakeReceipe item);
    }
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.bake_reciepe_text);
        }
        public void bind(final BakeReceipe item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BakeAdapter(List<BakeReceipe> myDataset, OnItemClickListener listener) {
        this.mDataset = myDataset;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BakeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_recipe_view, parent, false);

        ViewHolder vh = new ViewHolder(inflate);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        System.out.println(mDataset.get(position).getName());
        holder.mTextView.setText(mDataset.get(position).getName());
        holder.bind(mDataset.get(position), listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void add(int position, BakeReceipe item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(Movie item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

}


