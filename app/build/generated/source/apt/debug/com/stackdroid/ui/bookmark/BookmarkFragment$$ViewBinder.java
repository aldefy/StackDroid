// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui.bookmark;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class BookmarkFragment$$ViewBinder<T extends com.stackdroid.ui.bookmark.BookmarkFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624066, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131624066, "field 'recyclerView'");
  }

  @Override public void unbind(T target) {
    target.recyclerView = null;
  }
}
