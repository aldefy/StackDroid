// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PortfolioRvAdapter$ViewHolder$$ViewBinder<T extends com.stackdroid.ui.PortfolioRvAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624051, "field 'userProfileImageView'");
    target.userProfileImageView = finder.castView(view, 2131624051, "field 'userProfileImageView'");
    view = finder.findRequiredView(source, 2131624052, "field 'questionTitleTextView'");
    target.questionTitleTextView = finder.castView(view, 2131624052, "field 'questionTitleTextView'");
    view = finder.findRequiredView(source, 2131624054, "field 'userNameTextView'");
    target.userNameTextView = finder.castView(view, 2131624054, "field 'userNameTextView'");
    view = finder.findRequiredView(source, 2131624053, "field 'timeLabel'");
    target.timeLabel = finder.castView(view, 2131624053, "field 'timeLabel'");
    view = finder.findRequiredView(source, 2131624055, "field 'tagIcon'");
    target.tagIcon = finder.castView(view, 2131624055, "field 'tagIcon'");
    view = finder.findRequiredView(source, 2131624056, "field 'tagTextView'");
    target.tagTextView = finder.castView(view, 2131624056, "field 'tagTextView'");
  }

  @Override public void unbind(T target) {
    target.userProfileImageView = null;
    target.questionTitleTextView = null;
    target.userNameTextView = null;
    target.timeLabel = null;
    target.tagIcon = null;
    target.tagTextView = null;
  }
}
