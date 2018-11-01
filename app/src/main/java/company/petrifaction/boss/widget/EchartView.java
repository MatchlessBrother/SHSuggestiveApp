package company.petrifaction.boss.widget;

import android.webkit.WebView;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebSettings;

public class EchartView extends WebView
{
    private static final String TAG = EchartView.class.getSimpleName();

    public EchartView(Context context)
    {
        this(context, null);
    }

    public EchartView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public EchartView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

    public void refreshEchartsViewWithDataJson(String dataJson)
    {
        if(dataJson == null)return;
        String call = "javascript:loadEcharts('" + dataJson + "')";
        loadUrl(call);
    }
}