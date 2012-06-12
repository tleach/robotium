package com.jayway.android.robotium.solo;

import junit.framework.Assert;
import android.app.Activity;
import android.app.Instrumentation;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class Solo2 extends Solo {

  /**
   * @param instrumentation
   * @param activity
   */
  public Solo2(Instrumentation instrumentation, Activity activity) {
    super(instrumentation, activity);
  }

  /**
   * @param instrumentation
   */
  public Solo2(Instrumentation instrumentation) {
    super(instrumentation);
  }
  
  public Solo2 click(int resId) {
    clickOnView(getView(resId));
    return this;
  }
  
  public Solo2 navigateBack() {
    super.goBack();
    return this;
  }
  
  public static ViewLocator<EditText> editText(int resId) {
    return new ViewLocator<EditText>(EditText.class, resId);
  }
  
  public EditTextSelector enter(String text) {
    return new EditTextSelector(text);
  }
  
  public Solo2 clear(int resId) {
    clearEditText((EditText)getView(resId));
    return this;
  }
  
  public <T extends Activity> ActivityExpectation<T> and(Class<T> clazz) {
    return new ActivityExpectation<T>(clazz);
  }  
  
  public EditTextExpectation and(ViewLocator<EditText> viewLocator) {
    EditText editText = (EditText) getView(viewLocator.resId);
    return new EditTextExpectation(editText);
  }
  
  public TextExpectation and(int stringId) {
    return new TextExpectation(stringId);
  }
  
  public class EditTextSelector {
    private final String text;
    
    public EditTextSelector(String text) {
      this.text = text;
    }
    
    public Solo2 into(int resId) {
      enterText((EditText) getView(resId), text);
      return Solo2.this;
    }
  }
  
  
  public class EditTextExpectation {
    private final EditText editText;
    
    public EditTextExpectation(EditText editText) {
      this.editText = editText;
    }
    
    public Solo2 shouldContain(String text) {
      Assert.assertTrue(TextUtils.equals(text, editText.getText()));
      return Solo2.this;
    }
    
    public Solo2 shouldBeEmpty() {
      Assert.assertTrue(TextUtils.isEmpty(editText.getText()));
      return Solo2.this;
    }
  }
  
  public class TextExpectation {
    private final int stringId;

    /**
     * @param stringId
     */
    public TextExpectation(int stringId) {
      super();
      this.stringId = stringId;
    }
    
    public Solo2 shouldAppear() {
      Assert.assertTrue(waitForText(stringId));
      return Solo2.this;
    }
  }
  
  public class ActivityExpectation<T extends Activity> {
    private final Class<T> clazz;
    public ActivityExpectation(Class<T> clazz) {
      this.clazz = clazz;
    }
    
    public Solo2 shouldAppear() {
      Assert.assertTrue(waitForActivity(clazz));
      return Solo2.this;
    }
  }
  
  public static class ViewLocator<T extends View> {
    final Class<T> clazz;
    final int resId;
    /**
     * @param clazz
     * @param resId
     */
    public ViewLocator(Class<T> clazz, int resId) {
      super();
      this.clazz = clazz;
      this.resId = resId;
    }
    
    
  }
}
