// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PortfolioFragement$$ViewBinder<T extends com.stackdroid.ui.PortfolioFragement> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624069, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131624069, "field 'recyclerView'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
