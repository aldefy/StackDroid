// Generated code from Butter Knife. Do not modify!
package com.stackdroid.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.stackdroid.ui.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624044, "field 'dashboardToolbar'");
    target.dashboardToolbar = finder.castView(view, 2131624044, "field 'dashboardToolbar'");
    view = finder.findRequiredView(source, 2131624043, "field 'appbar'");
    target.appbar = finder.castView(view, 2131624043, "field 'appbar'");
    view = finder.findRequiredView(source, 2131624045, "field 'fragmentContainer'");
    target.fragmentContainer = finder.castView(view, 2131624045, "field 'fragmentContainer'");
    view = finder.findRequiredView(source, 2131624042, "field 'coordinator'");
    target.coordinator = finder.castView(view, 2131624042, "field 'coordinator'");
    view = finder.findRequiredView(source, 2131624047, "field 'navigationView'");
    target.navigationView = finder.castView(view, 2131624047, "field 'navigationView'");
    view = finder.findRequiredView(source, 2131624041, "field 'drawerLayout'");
    target.drawerLayout = finder.castView(view, 2131624041, "field 'drawerLayout'");
    view = finder.findRequiredView(source, 2131624046, "field 'fab'");
    target.fab = finder.castView(view, 2131624046, "field 'fab'");
  }

  @Override public void unbind(T target) {
    target.dashboardToolbar = null;
    target.appbar = null;
    target.fragmentContainer = null;
    target.coordinator = null;
    target.navigationView = null;
    target.drawerLayout = null;
    target.fab = null;
  }
}
