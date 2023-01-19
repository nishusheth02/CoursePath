package com.example.schedulab;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.schedulab.refactoring.Model;
import com.example.schedulab.refactoring.Presenter;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock

    MainActivity view;

    @Mock
    Model model;


   @Test
    public void testEmptyEmail(){
        //when(view.getEmail()).thenReturn("");
       // when(model.checkLogin("", "123456")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.loginButtonClicked("", "123456");
        verify(view).emailEmpty();

    }


    @Test
    public void testEmptyPassword(){

        //when(view.getPassword()).thenReturn("");
        //when(model.checkLogin("bye@gmail.com", "")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.loginButtonClicked("bye@gmail.com", "" );
        verify(view).passwordEmpty();

    }

   @Test
    public void testInvalidEmail(){
        //when(view.getEmail()).thenReturn("n");
       //when(model.checkLogin("n", "123456")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.loginButtonClicked("n", "123456" );
        verify(view).emailNotValid();

    }

    @Test
    public void testCheckLoginFalse(){
        when(model.checkLogin("nishu@gmail.com", "123456")).thenReturn(false);
        Presenter presenter = new Presenter(model, view);
        presenter.login("nishu@gmail.com", "123456" );
        verify(view).onFailure();

    }

    @Test
    public void testCheckLoginTrue(){
        when(model.checkLogin("nishu@gmail.com", "123456")).thenReturn(true);
        Presenter presenter = new Presenter(model, view);
        presenter.login("nishu@gmail.com", "123456" );
        verify(view).onSuccess();

    }

    @Test
    public void testFieldsValid(){
        Presenter presenter = new Presenter(model, view);
        assertTrue(presenter.loginButtonClicked("nishu@gmail.com", "123456" ));

    }








}