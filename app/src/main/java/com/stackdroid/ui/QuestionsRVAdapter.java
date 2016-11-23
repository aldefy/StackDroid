package com.stackdroid.ui;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.volokh.danylo.hashtaghelper.HashTagHelper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.uk.rushorm.core.RushCore;
import co.uk.rushorm.core.RushSearch;

import com.stackdroid.R;
import com.stackdroid.api.models.QItems;
import com.stackdroid.api.models.QTags;
import com.stackdroid.utils.CircleImageView;
import com.stackdroid.utils.CommonUtils;

import timber.log.Timber;

/**
 * Created by aditlal on 12/04/16.
 */
public class QuestionsRVAdapter extends RecyclerView.Adapter<QuestionsRVAdapter.ViewHolder> implements Filterable {

    List<QItems> qItemList;
    List<QItems> originalList;
    Context context;
    char[] additionalSymbols;
    boolean isLike;
    ItemClick itemClickInterface;

    public QuestionsRVAdapter(Context context, List<QItems> qItemList, boolean isLike , ItemClick itemClickInterface) {
        this.qItemList = qItemList;
        this.isLike = isLike;
        this.originalList = qItemList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
        additionalSymbols = new char[]{
                '-'
        };
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_questions, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        QItems qItems = qItemList.get(position);
        Picasso.with(context).load(qItems.getOwner().getProfile_image()).placeholder(R.drawable.ic_person).into(holder.userProfileImageView);
        holder.questionTitleTextView.setText(qItems.getTitle());
        holder.userNameTextView.setText(qItems.getOwner().getDisplay_name());
        holder.timeLabel.setText(CommonUtils.toRelativeTime(new DateTime(Long.parseLong(qItems.getLast_activity_date()) * 1000, DateTimeZone.getDefault())));

        holder.tagTextView.setText("");
        if (qItems.getTags() != null) {
            for (String tag : qItems.getTags())
                holder.tagTextView.append("#" + tag + " ");
            HashTagHelper mTextHashTagHelper = HashTagHelper.Creator.create(ContextCompat.getColor(context, R.color.grey_500), null, additionalSymbols);

            mTextHashTagHelper.handle(holder.tagTextView);
        } else
            holder.tagTextView.append("#" + "android ");


        int score = Integer.parseInt(qItems.getScore());
        holder.scoreTextView.setText(score + "");
        if (score < 0)
            holder.scoreTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_arrow_down), null);
        else if (score > 0)
            holder.scoreTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(context, R.drawable.ic_arrow_up), null);
        else
            holder.scoreTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        holder.soLinkButton.setTag(qItems.getLink());
        holder.favButton.setTag(qItems);
        if (qItems.isFav())
            holder.favButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_selected_small));
        else
            holder.favButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_favorite_small));

        holder.soLinkButton.setOnClickListener(v -> CommonUtils.openLink(context, v));
        holder.favButton.setOnClickListener(v -> favQItem(v));


        runEnterAnimation(holder.itemView);
    }

    private void favQItem(View i) {
        QItems qItems = (QItems) i.getTag();
        itemClickInterface.itemClickedAt(qItems);
      /*  QItems qItems = (QItems) i.getTag();
        qItems.setFav(!qItems.isFav());
        notifyDataSetChanged();
        if (qItems.isFav()) {
            for (String tags : qItems.getTags()) {
                QTags qTags = new QTags();
                qTags.setTags(tags);
                qTags.setUrl(qItems.getLink());
                qTags.save();
            }
            qItems.save();
        } else {
            qItems.delete();
            List<QTags> searchList = new RushSearch().whereContains("url", qItems.getLink()).find(QTags.class);
            RushCore.getInstance().delete(searchList);
        }
        if(isLike)
            qItemList.remove(qItems);*/
    }

    @Override
    public int getItemCount() {
        return qItemList.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                qItemList = (ArrayList<QItems>) results.values;
                notifyDataSetChanged();

            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                Timber.tag("Filtering").d(constraint.toString());


                ArrayList<QItems> FilteredList = new ArrayList<QItems>();
                if (qItemList == null) {
                    qItemList = new ArrayList<QItems>(originalList);
                }
                if (qItemList.size() == 0) {
                    qItemList = new ArrayList<QItems>(originalList);
                }
                if (constraint == null || constraint.length() == 0) {
                    results.count = originalList.size();
                    results.values = originalList;

                } else {
                    constraint = constraint.toString().trim().toLowerCase(Locale.getDefault());
                    Timber.tag("Filtering").d(constraint.toString());
                    for (int i = 0; i < qItemList.size(); i++) {
                        for (String tag : qItemList.get(i).getTags())

                            if (tag.toLowerCase(Locale.getDefault()).contains(constraint)) {
                                FilteredList.add(qItemList.get(i));
                            } else {
                                Timber.tag("Filtering").d(constraint.toString());

                            }
                    }
                    results.count = FilteredList.size();
                    results.values = FilteredList;
                }
                return results;

            }

        };

        return filter;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.userProfileImageView)
        CircleImageView userProfileImageView;
        @Bind(R.id.questionTitleTextView)
        TextView questionTitleTextView;
        @Bind(R.id.userNameTextView)
        TextView userNameTextView;
        @Bind(R.id.timeLabel)
        TextView timeLabel;
        @Bind(R.id.tagIcon)
        ImageView tagIcon;
        @Bind(R.id.tagTextView)
        TextView tagTextView;
        @Bind(R.id.contentDivider)
        View contentDivider;
        @Bind(R.id.buttonLayout)
        LinearLayout buttonLayout;

        @Bind(R.id.favButton)
        ImageButton favButton;
        @Bind(R.id.scoreTextView)
        TextView scoreTextView;
        @Bind(R.id.soLinkButton)
        ImageButton soLinkButton;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    private void runEnterAnimation(View view) {
        view.setTranslationY(CommonUtils.getScreenHeight(context));
        view.animate()
                .translationY(0)
                .setInterpolator(new DecelerateInterpolator(3.f))
                .setDuration(700)
                .start();
    }


   public interface ItemClick {
        void itemClickedAt( QItems qItems);
    }

}
