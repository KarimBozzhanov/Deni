package com.example.deni;

public interface mySummaryClickListener {
    void onSummaryClickListener(Summary summary, int position);
    void onSummaryRefreshClickListener(Summary summary, int position);
    void onSummaryChangeSummaryClickListener(Summary summary, int position);
    void onSummaryDeleteSummaryClickListener(Summary summary, int position);
}
