package com.java.chenyuxiang.listViewUi;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.tabs.TabLayout;
import com.java.chenyuxiang.R;
import com.java.chenyuxiang.dataUI.ChartFragment;
import com.java.chenyuxiang.dataUI.ChartFragmentAdapter;
import com.java.tanghao.AppManager;
import com.java.tanghao.YiqingData;
import com.java.tanghao.YiqingDataManager;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class FragmentData extends Fragment implements OnChartValueSelectedListener {

    ArrayList<Fragment> fragments_china = new ArrayList<>();
    ArrayList<Fragment> fragments_world = new ArrayList<>();
    ArrayList<String> provinceList;
    ArrayList<String> countryList;
    YiqingData chinaTotal;
    YiqingData worldTotal;
    YiqingDataManager yiqingDataManager;
    final static Integer CONFIRM = 0;
    final static Integer SUSPECT = 1;
    final static Integer CURED = 2;
    final static Integer DEAD = 3;

    private static final String[] table_titles = new String[]{"现存确诊", "累计感染", "累计治愈", "累计死亡"};
    public static final String[] STAT_ENTRIES = new String[]{"curInfected", "totalInfected", "totalCured", "totalDead"};

    private LineChart chart;
    private TextView tvX, tvY;

    public FragmentData(ArrayList<String>list,ArrayList<String> list2) {
        yiqingDataManager = AppManager.getYiqingDataManager();
        yiqingDataManager.getPageYiqingData("https://covid-dashboard.aminer.cn/api/dist/epidemic.json");
        provinceList = list;
        countryList = list2;
        while (!yiqingDataManager.getIsReady()){
            try{
                Thread.sleep(10);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        chinaTotal = yiqingDataManager.getLocationYiqingData("China").get(0);
        worldTotal = yiqingDataManager.getLocationYiqingData("World").get(0);
        init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        // add charts
        ArrayList<Integer> xs;
        ArrayList<ArrayList<Integer>> ys;

        // world series
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        for (int i = 0; i < table_titles.length; i++)
            ys.add(new ArrayList<Integer>());
        Integer[][] data = worldTotal.getData();
        String[] temp = worldTotal.getBegin().split("-");
        LocalDate d = LocalDate.of(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
        for(int i=0;i<data.length;++i) {
            xs.add(d.getMonthValue() * 100 + d.getDayOfMonth());
            ys.get(0).add(data[i][CONFIRM]-data[i][CURED]-data[i][DEAD]);
            ys.get(1).add(data[i][CONFIRM]);
            ys.get(2).add(data[i][CURED]);
            ys.get(3).add(data[i][DEAD]);
            d=d.plusDays(1);
        }
        for (int i = 0; i < table_titles.length; i++)
            fragments_world.add(ChartFragment.newInstance(table_titles[i], xs, ys.get(i)));

        // china series
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        for (int i = 0; i < table_titles.length; i++)
            ys.add(new ArrayList<Integer>());
        data = chinaTotal.getData();
        temp = chinaTotal.getBegin().split("-");
        d = LocalDate.of(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]),Integer.parseInt(temp[2]));
        for(int i=0;i<data.length;++i) {
            xs.add(d.getMonthValue() * 100 + d.getDayOfMonth());
            ys.get(0).add(data[i][CONFIRM]-data[i][CURED]-data[i][DEAD]);
            ys.get(1).add(data[i][CONFIRM]);
            ys.get(2).add(data[i][CURED]);
            ys.get(3).add(data[i][DEAD]);
            d=d.plusDays(1);
        }
        for (int i = 0; i < table_titles.length; i++)
            fragments_china.add(ChartFragment.newInstance(table_titles[i], xs, ys.get(i)));

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_interface2, container, false);

        // setup charts
        TabLayout tab = root.findViewById(R.id.charts_china);
        ViewPager pager = (ViewPager) root.findViewById(R.id.pager_china);
        ChartFragmentAdapter adapter = new ChartFragmentAdapter(this.getActivity().getSupportFragmentManager(), getContext(), fragments_china);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        for(int i = 0; i < table_titles.length; i++)
            tab.getTabAt(i).setText(table_titles[i]);

        tab = root.findViewById(R.id.charts_world);
        pager = (ViewPager) root.findViewById(R.id.pager_world);
        adapter = new ChartFragmentAdapter(this.getActivity().getSupportFragmentManager(), getContext(), fragments_world);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);
        for(int i = 0; i < table_titles.length; i++)
            tab.getTabAt(i).setText(table_titles[i]);

        // inflate tables
        inflateSnapShotTable((TableLayout) root.findViewById(R.id.dataTable_China), getChinaStats());
        inflateSnapShotTable((TableLayout) root.findViewById(R.id.dataTable_World), getWorldStats());

        inflateDetailTable1((TableLayout) root.findViewById(R.id.detail_table_china), provinceDataSnapshot, provinceDetailDataSnapshot);
        inflateDetailTable2(root.findViewById(R.id.detail_table_world), keyCountrySnapshot, 20);
        return root;
    }

    class SnapShot{
        int confirm;
        int cure;
        int dead;
        SnapShot(int a,int b,int c){
            confirm=a;
            cure=b;
            dead=c;
        }

    }


    private LinkedHashMap<String,SnapShot> provinceDataSnapshot;
    public Map<String, LinkedHashMap<String, SnapShot>> provinceDetailDataSnapshot;
    public LinkedHashMap<String,SnapShot> keyCountrySnapshot;
    private HashMap<String, SnapShot> tmpWorldSnapshot;

    private void init(){
        tmpWorldSnapshot = new HashMap<>();
        provinceDataSnapshot = new LinkedHashMap<>();
        provinceDetailDataSnapshot = new LinkedHashMap<>();
        for(int i=0;i<countryList.size();++i){
           String location = countryList.get(i);
           YiqingData temp = yiqingDataManager.getLocationYiqingData(location).get(0);
           Integer[][] data = temp.getData();
           int size = data.length;
           SnapShot snapShot = new SnapShot(data[size-1][CONFIRM],data[size-1][CURED],data[size-1][DEAD]);
           if(location.equals("United States of America"))
               location = "U.S.A";
           tmpWorldSnapshot.put(location,snapShot);
        }

        for(int i=0;i<provinceList.size();++i){
            String location = "China|"+provinceList.get(i);
            YiqingData temp = yiqingDataManager.getLocationYiqingData(location).get(0);
            Integer[][] data = temp.getData();
            int size = data.length;
            SnapShot snapShot = new SnapShot(data[size-1][CONFIRM],data[size-1][CURED],data[size-1][DEAD]);
            provinceDataSnapshot.put(provinceList.get(i),snapShot);

            LinkedHashMap<String ,SnapShot> tmp = new LinkedHashMap<>();
            ArrayList<YiqingData> provDetail = yiqingDataManager.getStreetYiqingData(location+"|");
            for(int j=0;j<provDetail.size();++j){
                temp = provDetail.get(j);
                String city = temp.getLocation().split("\\|")[2];
                data = temp.getData();
                size = data.length;
                snapShot = new SnapShot(data[size-1][CONFIRM],data[size-1][CURED],data[size-1][DEAD]);
                tmp.put(city,snapShot);
            }
            provinceDetailDataSnapshot.put(
                    provinceList.get(i),
                    tmp.entrySet()
                            .stream()
                            .sorted((e1, e2) -> {
                                return e1.getValue().confirm - e2.getValue().confirm;
                            })
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> {
                                return e1;
                            }, LinkedHashMap::new))
            );
        }
        provinceDataSnapshot = provinceDataSnapshot.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    return e2.getValue().confirm-e1.getValue().confirm;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        keyCountrySnapshot = tmpWorldSnapshot.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    return e2.getValue().confirm-e1.getValue().confirm;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    public Map<String, Map<String, Integer>> getChinaStats() {
        Integer[][] chinaData = chinaTotal.getData();
        Integer chinaSize = chinaData.length;

        HashMap<String, Integer> cumulative = new HashMap<>();
        int curInfected = chinaData[chinaSize-1][CONFIRM] - chinaData[chinaSize-1][CURED] - chinaData[chinaSize-1][DEAD];
        cumulative.put(STAT_ENTRIES[0], curInfected);
        cumulative.put(STAT_ENTRIES[1], chinaData[chinaSize-1][CONFIRM]);
        cumulative.put(STAT_ENTRIES[2], chinaData[chinaSize-1][CURED]);
        cumulative.put(STAT_ENTRIES[3], chinaData[chinaSize-1][DEAD]);

        HashMap<String, Integer> change = new HashMap<>();
        change.put(STAT_ENTRIES[0], curInfected - (chinaData[chinaSize-2][CONFIRM]-chinaData[chinaSize-2][CURED]-chinaData[chinaSize-2][DEAD]));
        change.put(STAT_ENTRIES[1], chinaData[chinaSize-1][CONFIRM] - chinaData[chinaSize-2][CONFIRM]);
        change.put(STAT_ENTRIES[2], chinaData[chinaSize-1][CURED] - chinaData[chinaSize-2][CONFIRM]);
        change.put(STAT_ENTRIES[3], chinaData[chinaSize-1][DEAD] - chinaData[chinaSize-2][CONFIRM]);

        HashMap<String, Map<String, Integer>> ret = new HashMap<>();
        ret.put("cumulative", cumulative);
        ret.put("change", change);
        return ret;
    }

    public Map<String, Map<String, Integer>> getWorldStats() {
        Integer[][] chinaData = worldTotal.getData();
        Integer chinaSize = chinaData.length;

        HashMap<String, Integer> cumulative = new HashMap<>();
        int curInfected = chinaData[chinaSize-1][CONFIRM] - chinaData[chinaSize-1][CURED] - chinaData[chinaSize-1][DEAD];
        cumulative.put(STAT_ENTRIES[0], curInfected);
        cumulative.put(STAT_ENTRIES[1], chinaData[chinaSize-1][CONFIRM]);
        cumulative.put(STAT_ENTRIES[2], chinaData[chinaSize-1][CURED]);
        cumulative.put(STAT_ENTRIES[3], chinaData[chinaSize-1][DEAD]);

        HashMap<String, Integer> change = new HashMap<>();
        change.put(STAT_ENTRIES[0], curInfected - (chinaData[chinaSize-2][CONFIRM]-chinaData[chinaSize-2][CURED]-chinaData[chinaSize-2][DEAD]));
        change.put(STAT_ENTRIES[1], chinaData[chinaSize-1][CONFIRM] - chinaData[chinaSize-2][CONFIRM]);
        change.put(STAT_ENTRIES[2], chinaData[chinaSize-1][CURED] - chinaData[chinaSize-2][CONFIRM]);
        change.put(STAT_ENTRIES[3], chinaData[chinaSize-1][DEAD] - chinaData[chinaSize-2][CONFIRM]);

        HashMap<String, Map<String, Integer>> ret = new HashMap<>();
        ret.put("cumulative", cumulative);
        ret.put("change", change);
        return ret;
    }




    private void inflateSnapShotTable(TableLayout table, Map<String, Map<String, Integer>> data) {
        TextView t;

        Map<String, Integer> cum = data.get("cumulative");
        t = table.findViewById(R.id.textCurrentSick);
        t.setText(Integer.toString(cum.get(STAT_ENTRIES[0])));
        t = table.findViewById(R.id.textTotalSick);
        t.setText(Integer.toString(cum.get(STAT_ENTRIES[1])));
        t = table.findViewById(R.id.textTotalCured);
        t.setText(Integer.toString(cum.get(STAT_ENTRIES[2])));
        t = table.findViewById(R.id.textTotalDeath);
        t.setText(Integer.toString(cum.get(STAT_ENTRIES[2])));

        cum = data.get("change");
        t = table.findViewById(R.id.textNewCurSick);
        int val = cum.get(STAT_ENTRIES[0]);
        t.setText((val > 0 ? "+" : "") + Integer.toString(val));
        t = table.findViewById(R.id.textNewTotalSick);
        val = cum.get(STAT_ENTRIES[1]);
        t.setText((val > 0 ? "+" : "") + Integer.toString(val));
        t = table.findViewById(R.id.textNewTotalCured);
        val = cum.get(STAT_ENTRIES[2]);
        t.setText((val > 0 ? "+" : "") + Integer.toString(val));
        t = table.findViewById(R.id.textNewTotalDeath);
        val = cum.get(STAT_ENTRIES[3]);
        t.setText((val > 0 ? "+" : "") + Integer.toString(val));
    }

    private void inflateDetailTable1(TableLayout table, Map<String, SnapShot> sum, Map<String, LinkedHashMap<String, SnapShot>> details) {
        if (sum == null)
            return;
        boolean odd = false;
        for(String prov: sum.keySet()) {
            TableRow tbRow = new TableRow(this.getContext());
            tbRow.setPadding(0, 1, 0, 1);
            //#EAEFDD
            tbRow.setBackgroundColor(odd ? Color.WHITE : Color.rgb(0xEA, 0xEF, 0xDD));
            odd = !odd;

            SnapShot e = sum.get(prov);
            boolean hasDetail = details.containsKey(prov);
            tbRow.addView(hasDetail ? getTextView("\u25B6", prov) : getTextView(prov));
            tbRow.addView(getTextView(dispCurConfirmed(e.confirm,e.cure,e.dead)));
            tbRow.addView(getTextView(dispConfirmed(e.confirm)));
            tbRow.addView(getTextView(dispDead(e.dead)));
            tbRow.addView(getTextView(dispCured(e.cure)));

            table.addView(tbRow);

            if(hasDetail) {
                List<TableRow> subTable = new ArrayList<>();
                for (Map.Entry<String, SnapShot> entry: details.get(prov).entrySet()) {
                    TableRow subR = new TableRow(this.getContext());
                    subR.setPadding(0, 1, 0, 1);
                    subR.setBackgroundColor(Color.WHITE);

                    String city = entry.getKey();
                    city = city.length() > 8 ? city.substring(0, 8) : city;
                    subR.addView(getTextView("  " + city));

                    SnapShot en = entry.getValue();
                    subR.addView(getTextView(dispCurConfirmed(en.confirm,en.cure,en.dead)));
                    subR.addView(getTextView(dispConfirmed(en.confirm)));
                    subR.addView(getTextView(dispDead(en.dead)));
                    subR.addView(getTextView(dispCured(en.cure)));
                    subR.setVisibility(View.GONE);
                    table.addView(subR);
                    subTable.add(subR);
                }
                tbRow.getChildAt(0).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView t = (TextView)((LinearLayout)view).getChildAt(0);
                        if (t.getText().equals("\u25B6"))
                            t.setText("\u25BC");
                        else
                            t.setText("\u25B6");
                        for(TableRow row: subTable) {
                            row.setVisibility(8 - row.getVisibility());
                        }
                    }
                });
            }
        }

    }

    private void inflateDetailTable2(TableLayout table, Map<String, SnapShot> sum, int topN) {
        if (sum == null)
            return;

        int i = 0;
        for(String prov: sum.keySet()) {
            TableRow tbRow = new TableRow(this.getContext());
            tbRow.setPadding(0, 1, 0, 1);
            tbRow.setBackgroundColor(i%2==0 ? Color.WHITE : Color.rgb(0xEA, 0xEF, 0xDD));

            SnapShot e = sum.get(prov);
            tbRow.addView(getTextView(prov));
            tbRow.addView(getTextView(dispCurConfirmed(e.confirm,e.cure,e.dead)));
            tbRow.addView(getTextView(dispConfirmed(e.confirm)));
            tbRow.addView(getTextView(dispDead(e.dead)));
            tbRow.addView(getTextView(dispCured(e.cure)));

            table.addView(tbRow);
            if (++i >= topN) {
                break;
            }
        }
    }

    private LinearLayout getTextView(String prefix, String text) {
        TableRow.LayoutParams l = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        l.setMargins(1, 0, 0, 0);
        LinearLayout cell = new LinearLayout(this.getContext());
        cell.setLayoutParams(l);
        if(prefix != null) {
            TextView p = new TextView(this.getContext());
            p.setText(prefix);
            cell.addView(p);
        }
        TextView t = new TextView(this.getContext());
        t.setText(text);
        t.setPadding(5, 5, 5, 5);
        cell.addView(t);
        return cell;
    }

    public String dispCurConfirmed(int confirmed,int cured,int dead) {
        return NumberFormat.getNumberInstance(Locale.US).format(confirmed - cured - dead);
    }

    public String dispConfirmed(int confirmed) {
        return NumberFormat.getNumberInstance(Locale.US).format(confirmed);
    }

    public String dispCured(int cured) {
        return NumberFormat.getNumberInstance(Locale.US).format(cured);
    }

    public String dispDead(int dead) {
        return NumberFormat.getNumberInstance(Locale.US).format(dead);
    }



    private LinearLayout getTextView(String text) {
        return getTextView(null, text);
    }

    private final int[] colors = new int[] {
            ColorTemplate.VORDIPLOM_COLORS[0],
            ColorTemplate.VORDIPLOM_COLORS[1],
            ColorTemplate.VORDIPLOM_COLORS[2]
    };


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

        public void onNothingSelected() {}
}


