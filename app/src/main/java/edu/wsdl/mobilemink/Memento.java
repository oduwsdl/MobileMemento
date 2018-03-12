package edu.wsdl.mobilemink;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

/**
 * An individual record of archive of a given website.
 * @author wes
 */
public class Memento {

    private ScreenType screenType;
    private Date date;
    private String contentUrl, archiveUrl;

    public static final String[] keys = {"title", "subtitle"};

    public Memento(String contentUrl, String parseText, ScreenType screenType) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
        this.contentUrl = contentUrl;
        this.archiveUrl = parseText.substring(parseText.indexOf('<') + 1, parseText.indexOf("/>"));

        //System.out.println(archiveURL);

        int index = parseText.indexOf("datetime=\"") + "datetime=\"".length();
        String dateText = parseText.substring(index, parseText.indexOf('"', index));
        //Log.d("New Memento", dateText);
        date = format.parse(dateText);

        this.screenType = screenType;
    }

    /**
     * Creates a memento with all properties set.
     * @param screenType a <code>ScreenType</code> object describing the DPI of website that the memento represents.
     * @param date a <code>Date</code> object the archive was made
     * @param url a <code>String</code> consisting of the URL of the archived page
     * @param archiveUrl a <code>MementoSource</code> object noting database that the memento originates from
     */
    public Memento(ScreenType screenType, Date date, String url, String archiveUrl) {
        this.screenType = screenType;
        this.date = date;
        this.contentUrl = url;
        this.archiveUrl = archiveUrl;
    }

    /**
     * Gets the displayed title of the memento, (usually the date)
     * @return the displayed title of the memento, (usually the date, formatted to the user's locale)
     */
    public String getTitle() {
        return DateFormat.getDateInstance().format(date);
    }

    /**
     * Gets the displayed subtitle of the memento, (usually the time of the archive)
     * @return the displayed subtitle of the memento, (usually the time, formatted to the user's
     * locale and set to UTC)
     */
    public String getSubtitle() {
        DateFormat format = DateFormat.getTimeInstance();
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return DateFormat.getTimeInstance().format(date);
    }

    /**
     * Returns the date-time of the memento, formatted to a custom locale.
     * @param format The custom locale to format the date to.
     * @return A <code>String</code> containing the formatted date
     */
    public String getTime(DateFormat format) {
        DateFormat df = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        String reportDate = df.format(date);

        //return format.format(date) + " GMT";
        return reportDate + " GMT";
    }

    public ScreenType getScreenType() {
        return screenType;
    }

    public void setScreenType(ScreenType screenType) {
        this.screenType = screenType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getArchiveUrl() { return archiveUrl; }

    public void setArchiveUrl(String archiveUrl) {
        this.archiveUrl = archiveUrl;
    }

    /**
     * Custom comparator between two dates in mementos.
     * @param date1
     * @param date2
     * @return true if the two dates are equal.
     */
    static boolean dateCompare(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();

        calendar1.setTime(date1);
        calendar1.setTime(date2);

        int d1Year = calendar1.get(Calendar.YEAR);
        int d1Month = calendar1.get(Calendar.MONTH);
        int d1Date = calendar1.get(Calendar.DATE);
        int d2Year = calendar2.get(Calendar.YEAR);
        int d2Month = calendar2.get(Calendar.MONTH);
        int d2Date = calendar2.get(Calendar.DATE);

        return (d1Year == d2Year &&
                d1Month == d2Month &&
                d1Date == d2Date);
    }
}
