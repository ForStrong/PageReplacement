package com.zhangsiyuan.pagereplacement;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PagerReplaceUtils {
    private List<Integer> list;
    private int n;//叶框数
    private int m;//访问次数
    private int F;//没有直接找到的次数 f/m 是缺页率
    public HashMap<Integer, Integer> map = new HashMap<>();

    PagerReplaceUtils(List<Integer> list, int leafBox) {
        this.list = list;
        this.n = leafBox;
        m = list.size();
    }


    public float OPT() {
        map.clear();
        F = 0;
        int j;
        for (int i = 0; i < m; i++) {
            int k = list.get(i);//待处理元素
            //System.out.print(map);
            if (!map.containsValue(k)) {
                F++;//不能直接找到次数加1
                if (map.size() < n) {//如果没有装满
                    int temp = map.size();
                    map.put(temp, k);
                } else {//如果装满了
                    int index = 0;//把哪个位置的淘汰出去
                    int min = 0;//初始最长长度
                    for (int t = 0; t < n; t++) {
                        for (j = i + 1; j < m; j++) {//看后面哪一个出现的最晚
                            if (list.get(j) == map.get(t)) {//第一次找到
                                if (j - i > min) {
                                    index = t;//更新值
                                    min = j - i;
                                }
                                break;
                            }
                        }
                        if (j == m) {//如果到最后
                            index = t;
                            min = j - i;
                        }
                    }
                    map.remove(index);
                    map.put(index, k);//修改表内元素
                }
            }
        }
        return (float) (F*1.0/m);
    }



    public float FIFO() {
        map.clear();
        F = 0;
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            int k = list.get(i);//待处理元素
            if (!map.containsValue(k)) {
                F++;//不能直接找到次数加1
                if (map.size() < n) {//如果没有装满
                    int temp = map.size();
                    map.put(temp, k);
                    q.offer(temp);
                } else {
                    int temp = q.poll();//排除的元素位置
                    map.remove(temp);
                    map.put(temp, k);
                    q.offer(temp);
                }
            }
        }
        return (float) (F * 1.0 / m);
    }

    public float LRU() {//最近最远未使用置换算法
        map.clear();
        F = 0;
        List<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            int k = list.get(i);//待处理元素
            if (!map.containsKey(k)) {
                F++;//不能直接找到次数加1
                if (map.size() < n) {//如果没有装满
                    int temp = map.size();
                    map.put(k, temp);
                    linkedList.add(k);//添加位置
                } else {
                    int temp = linkedList.get(0);
                    int c = map.get(temp);//位置
                    map.remove(temp);
                    map.put(k, c);
                    linkedList.remove(0);
                    linkedList.add(k);
                }
            } else//如果包含这个值，把这个值拿走并在后面压入
            {
                int d = linkedList.indexOf(k);//查找存在位置
                linkedList.remove(d);
                linkedList.add(k);
            }
        }
        return (float) (F * 1.0 / m);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
