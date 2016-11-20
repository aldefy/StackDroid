// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui.home;

import android.content.res.Resources;
import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HomeFragment$$ViewBinder<T extends com.stackdroid.ui.home.HomeFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624066, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131624066, "field 'recyclerView'");
    Resources res = finder.getContext(source).getResources();
    target.sort_activity_title = res.getString(2131230756);
    target.sort_creation_title = res.getString(2131230758);
    target.sort_votes_title = res.getString(2131230762);
    target.sort_relevance_title = res.getString(2131230761);
    target.sort_activity = res.getString(2131230757);
    target.sort_creation = res.getString(2131230759);
    target.sort_votes = res.getString(2131230763);
    target.sort_relevance = res.getString(2131230760);
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
