package ltd.hujing.myaccount.ui.analyse;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ltd.hujing.myaccount.R;
import ltd.hujing.myaccount.db.ChartItemBean;
import ltd.hujing.myaccount.db.DBManager;


public class IncomeChartFragment extends  BaseChartFragment {
    private int kind = 1;
    @Override
    public void onResume() {
        super.onResume();
        loadData(year, month, kind);

    }

    @Override
    public void setDate(int year, int month) {
        this.year = year;
        this.month = month;
        loadData(year,month,kind);
    }
}