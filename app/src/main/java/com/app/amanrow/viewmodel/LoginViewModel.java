package com.app.amanrow.viewmodel;

import com.app.amanrow.pojo.ReqLoginData;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel
{
    public ReqLoginData reqLoginData=new ReqLoginData();


    public void onLoginClicked()
    {
        System.out.println("button is clicked : "+reqLoginData.getUser_pwd()+" : "+reqLoginData.getUser_pwd());
       // showToast("button is clicked : "+viewModel.reqLoginData.getUser_pwd()+" : "+viewModel.reqLoginData.getUser_pwd());

    }
    public void validation()
    {

    }

}
