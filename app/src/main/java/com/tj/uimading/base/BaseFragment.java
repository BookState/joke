package com.tj.uimading.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {

    protected OnFragmentInteractionListener mListener;
    protected View rootView;
    protected BaseLoadingPager loadingPager;

    public BaseFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            loadingPager = new BaseLoadingPager(getContext()) {
                @Override
                protected void parseData(String result) {
                    parseRealData(result);
                }

                @Override
                protected String getUrl() {
                    return getRealUrl();
                }

                @Override
                protected int getSuccessLayout() {
                    return getRealLayout();
                }

                @Override
                protected void bindSuccessview(View view_success) {
                    ButterKnife.bind(BaseFragment.this, view_success);
                    initData();
                }
            };
            rootView = loadingPager;
        }
        return rootView;
    }

    protected abstract int getRealLayout();

    protected abstract String getRealUrl();

    protected abstract void parseRealData(String result);

    protected abstract void initData();

    //无参的showSuccessPager()里面调用loadingPager.netWorking()获取服务器数据的方法。
    public void showSuccessPager() {
        loadingPager.netWorking();
    }

    //有参的showSuccessPager()里面调用loadingPager.setUrl()添加Url的方法。
    public void showSuccessPager(String url){
        loadingPager.setUrl(url);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            parent.removeView(rootView);
        }
    }

    //    @Override
    //    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    //        super.onViewCreated(view, savedInstanceState);
    //        ButterKnife.bind(this, view);
    //        initData();
    //    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int viewId, Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(viewId, bundle);
        }
    }
    //    public void onButtonPressed(Uri uri) {
    //        if (mListener != null) {
    //            mListener.onFragmentInteraction(uri);
    //        }
    //    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int viewId, Bundle bundle);
    }
}
