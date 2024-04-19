// Generated by view binder compiler. Do not edit!
package com.example.myapplication.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.myapplication.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnMyRegister;

  @NonNull
  public final Button btnQueryCount;

  @NonNull
  public final EditText etRp1;

  @NonNull
  public final EditText etRp2;

  @NonNull
  public final EditText etRu1;

  @NonNull
  public final EditText etRu2;

  @NonNull
  public final TextView tvUserCount;

  private ActivityRegisterBinding(@NonNull LinearLayout rootView, @NonNull Button btnMyRegister,
      @NonNull Button btnQueryCount, @NonNull EditText etRp1, @NonNull EditText etRp2,
      @NonNull EditText etRu1, @NonNull EditText etRu2, @NonNull TextView tvUserCount) {
    this.rootView = rootView;
    this.btnMyRegister = btnMyRegister;
    this.btnQueryCount = btnQueryCount;
    this.etRp1 = etRp1;
    this.etRp2 = etRp2;
    this.etRu1 = etRu1;
    this.etRu2 = etRu2;
    this.tvUserCount = tvUserCount;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_my_register;
      Button btnMyRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnMyRegister == null) {
        break missingId;
      }

      id = R.id.btn_query_count;
      Button btnQueryCount = ViewBindings.findChildViewById(rootView, id);
      if (btnQueryCount == null) {
        break missingId;
      }

      id = R.id.et_rp1;
      EditText etRp1 = ViewBindings.findChildViewById(rootView, id);
      if (etRp1 == null) {
        break missingId;
      }

      id = R.id.et_rp2;
      EditText etRp2 = ViewBindings.findChildViewById(rootView, id);
      if (etRp2 == null) {
        break missingId;
      }

      id = R.id.et_ru1;
      EditText etRu1 = ViewBindings.findChildViewById(rootView, id);
      if (etRu1 == null) {
        break missingId;
      }

      id = R.id.et_ru2;
      EditText etRu2 = ViewBindings.findChildViewById(rootView, id);
      if (etRu2 == null) {
        break missingId;
      }

      id = R.id.tv_user_count;
      TextView tvUserCount = ViewBindings.findChildViewById(rootView, id);
      if (tvUserCount == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((LinearLayout) rootView, btnMyRegister, btnQueryCount,
          etRp1, etRp2, etRu1, etRu2, tvUserCount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
