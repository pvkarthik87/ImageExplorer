package com.karcompany.imageexplorer.views.fragments;

/**
 * Created by pvkarthik on 2017-01-12.
 *
 * BasedialogFragment from which other dialog fragments extend to Simplify DI, view injections.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;

import com.karcompany.imageexplorer.di.HasComponent;

import butterknife.ButterKnife;

public class BaseDialogFragment extends DialogFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViews(view);
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    /**
     * ButterKnife injection
     * @param view
     */
    private void bindViews(View view) {
        ButterKnife.bind(this, view);
    }

    private void unBindViews() {
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindViews();
    }
}
