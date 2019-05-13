package com.example.thinkpad.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.thinkpad.myapplication.view.FlipView;
import com.example.thinkpad.myapplication.view.ProvinceUtil;
import com.example.thinkpad.myapplication.view.ProvinceView;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrofit 源码分析
//        retrofitCode();

//        ImageView imageView1 = (ImageView) findViewById(R.id.iv1);
//        ImageView imageView2 = (ImageView) findViewById(R.id.iv2);


//        ObjectAnimator animator1 = ObjectAnimator.ofFloat(flipView,"bottomFlip",45);
//        animator1.setDuration(8000);
//
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(flipView,"flipRotation",270);
//        animator2.setDuration(8000);
//
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(flipView,"topFlip",-45);
//        animator1.setDuration(8000);
//
//        AnimatorSet set = new AnimatorSet();
//        set.playSequentially(animator1,animator2,animator3);
//        set.start();

//        PropertyValuesHolder bottomFlip = PropertyValuesHolder.ofFloat("bottomFlip", 45);
//        PropertyValuesHolder flipRotation = PropertyValuesHolder.ofFloat("flipRotation", 270);
//        PropertyValuesHolder topFlip = PropertyValuesHolder.ofFloat("topFlip", -45);
//        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(flipView, bottomFlip, flipRotation, topFlip);
//        animator.setDuration(8000);
//        animator.start();


//        Bitmap avatar1 = getAvatar1(200);
//        Bitmap avatar2 = getAvatar2();
//
//        imageView1.setImageBitmap(avatar1);
//        imageView2.setImageBitmap(avatar2);


        // ProvinceView打开
//        ProvinceView flipView = (ProvinceView) findViewById(R.id.flipView);
//        ObjectAnimator animator = ObjectAnimator.ofObject(flipView, "province", new ProvinceTypeEvaluator(), "澳门特别行政区");
//        animator.setStartDelay(3000);
//        animator.setDuration(8000);
//        animator.start();
    }

    private void retrofitCode() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Single<List<Repo>> repos = service.listRepos("xqgdmg");

        repos.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Repo>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("chris", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("chris", "onError");
                    }

                    @Override
                    public void onNext(List<Repo> repos) {
                        Log.e("chris", "onNext");
                        Log.e("chris", "repos==" + repos.toString());
                    }
                });

//        repos.enqueue(new Callback<List<Repo>>() {
//            @Override
//            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
//                Log.e("chris", "Success!!");
//                Log.e("chris", "call==" + call.toString());
//                Log.e("chris", "response==" + response.toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<Repo>> call, Throwable t) {
//                Log.e("chris", "Fail!!");
//            }
//        });
    }

    class ProvinceTypeEvaluator implements TypeEvaluator<String> {

        @Override
        public String evaluate(float fraction, String startValue, String endValue) {
            Log.e("chris", "startValue==" + startValue);
            Log.e("chris", "endValue==" + endValue);
            int startIndex = ProvinceUtil.provinces.indexOf(startValue);
            int endIndex = ProvinceUtil.provinces.indexOf(endValue);
            int index = (int) (startIndex + (endIndex - startIndex) * fraction);
            return ProvinceUtil.provinces.get(index);
        }
    }


//    OkHttpClient client = new OkHttpClient();
//
//    String run(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        okhttp3.Response response = client.newCall(request).execute();
//        return response.body().string();
//    }

//    Bitmap getAvatar1(int width) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
//        options.inJustDecodeBounds = false;
//        options.inDensity = options.outWidth;
//        options.inTargetDensity = width;
//        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian, options);
//    }
//
//    Bitmap getAvatar2() {
//        return BitmapFactory.decodeResource(getResources(), R.drawable.avatar_rengwuxian);
//    }


}
