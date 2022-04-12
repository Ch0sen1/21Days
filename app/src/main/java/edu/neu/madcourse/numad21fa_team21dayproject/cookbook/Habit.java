package edu.neu.madcourse.numad21fa_team21dayproject.cookbook;

public class Habit {
    public String hname;
    public int pic;
    public int total_num;
    public String time;
    public String fre;
    public String htext;
    public int finished_num;
    public int days;
    public int curdays;
    public int highdays;
    public String credate;
    public int swit;
    public long lastClickTime;

    public Habit(String h, int p, int t, int f, String time, String fre, String htext, int d, int c, int high, String cre, int swit) {
        setHname(h);
        setPic(p);
        setTotal_num(t);
        setFinished_num(f);

        setTime(time);
        setFre(fre);
        setHtext(htext);
        setDays(d);

        setCurdays(c);
        setHighdays(high);
        setCredate(cre);
        setSwit(swit);
    }

    public Habit(String hname,int picId, int curdays,String htext){
        this.hname = hname;
        this.pic = picId;
        this.htext = htext;
        this.curdays = curdays;
    }

    public Habit(){}

    public int getSwit() {
        return swit;
    }

    public void setSwit(int swit) {
        this.swit = swit;
    }

    public int getCurdays() {
        return curdays;
    }

    public void setCurdays(int curdays) {
        this.curdays = curdays;
    }

    public int getHighdays() {
        return highdays;
    }

    public void setHighdays(int highdays) {
        this.highdays = highdays;
    }

    public String getCredate() {
        return credate;
    }

    public void setCredate(String credate) {
        this.credate = credate;
    }


    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getTotal_num() {
        return total_num;
    }

    public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFre() {
        return fre;
    }

    public void setFre(String fre) {
        this.fre = fre;
    }

    public String getHtext() {
        return htext;
    }

    public void setHtext(String htext) {
        this.htext = htext;
    }

    public int getFinished_num() {
        return finished_num;
    }

    public void setFinished_num(int finished_num) {
        this.finished_num = finished_num;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    ///
    public long getLastClickTime() {
        return lastClickTime;
    }

    public void setLastClickTime(long lastClickTime) {
        this.lastClickTime = lastClickTime;
    }
}
