// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui.home;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends com.stackdroid.ui.home.HomeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624069, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131624069, "field 'recyclerView'");
    Resources res = finder.getContext(source).getResources();
    target.sort_activity_title = res.getString(2131230765);
    target.sort_creation_title = res.getString(2131230767);
    target.sort_votes_title = res.getString(2131230771);
    target.sort_relevance_title = res.getString(2131230770);
    target.sort_activity = res.getString(2131230766);
    target.sort_creation = res.getString(2131230768);
    target.sort_votes = res.getString(2131230772);
    target.sort_relevance = res.getString(2131230769);
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
