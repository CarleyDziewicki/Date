/**
 * The SimpleDate class represents a simple date object with three fields - month, day, and year.
 * 
 * @author Carley Dziewicki
 *
 */

public class SimpleDate implements Comparable<SimpleDate> {
	
	private static final int MIN_YEAR = 1753; // minimum allowable year in a date

	private static final int DAYS_YEAR = 365;
	private static final int DAYS_LEAP_YEAR = 366;
	
	// Stores number of days in each month in a non-leap year
	// DAYS_IN_MONTH[0] is not used since 1 <= month <= 12
	private static final int[] DAYS_IN_MONTH = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	
	// Stores number of days lapsed to the beginning of each month since the beginning of the year (non-leap)
	// DAYS_THUS_FAR[0] is not used since 1 <= month <= 12
	private static final int[] DAYS_THUS_FAR = {0, 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
	
	// MONTH_NAMES[0] is not used since 1 <= month <= 12
	private static final String[] MONTH_NAMES = {"", "January", "February", "March", "April", "May", "June",
			"July", "August", "September", "October", "November", "December"};
	
	public static enum DAY {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
	
	private int month;	// 1 <= moth <= 12
	
	private int day;	// 1 <= day <= 31
	
	private int year;	// year >= 1753
	
	/**
	 * Constructs a SimpleDate object with month, day, and year field values set to 1, 1, and 1753 (1/1/1753).
	 */
	public SimpleDate() {
		this(1, 1, 1753);
	}
	
	/**
	 * Constructs a SimpleDate object with month, day, and year field values set to given argument values.
	 * 
	 * @param month month of the date
	 * @param day day of the date
	 * @param year year of the date
	 * 
	 * @throws IllegalArgumentException if argument values supplied do not represent a valid date
	 */
	public SimpleDate(int month, int day, int year) throws IllegalArgumentException {        
		
		if(!isValidDate(month, day, year)) throw new IllegalArgumentException ("Invalid date");
		
		this.month = month;
		this.day = day;
		this.year = year;
	}

	/**
	 * Returns the month of this date object.
	 * 
	 * @return month of this date
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Returns the day of this date object.
	 * 
	 * @return day of this date
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Returns the year of this date object.
	 * 
	 * @return year of this date
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Tests if values provided for month, day, and year represent a valid date.
	 * 
	 * @param month month of date
	 * @param day day of date
	 * @param year year of date
	 * 
	 * @return true if valid date and false otherwise
	 */
	public static boolean isValidDate(int month, int day, int year) {
		
		//first checks year, if not valid we don't need to check anything else
		if(year >= MIN_YEAR ) {
			//checks if month is in bounds
			if(month < 1 || month > 12) {
				return false;
			}
			//check if feb. day is correct
			if(month == 2) {
				if(isLeapYear(year) == true && (day <= 29 && day > 0)) {
					return true;
				} else if(day <= 28 && day > 0) {
					return true;
				}
			}
			//checking days in month is correct except feb.
			if(month == 1 || month > 2) {
				if(day < 1 || day > DAYS_IN_MONTH[month]) {
					return false;
				}
				return true;
			}
		}
		return false;
	} 

	/**
	 * Tests if the year specified by argument is a leap year.
	 * 
	 * @param year year to test
	 * 
	 * @return true if year year is leap year, otherwise false
	 */
	public static boolean isLeapYear(int year) {

		if(((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
			return true;
		}else 
			return false;
	}
	
	/**
	 * Tests if the year of this date object is a leap year.
	 * 
	 * @return true if year year is leap year, otherwise false
	 */
	public boolean isLeapYear() {
		return SimpleDate.isLeapYear(year);
	}
	
	/**
	 * Returns the number of days in a given month of a year.
	 * 
	 * @param month month
	 * @param year year
	 * 
	 * @return the number of days for the given month and year arguments
	 */
	public static int daysInMonth(int month, int year) {

		if(month != 2) {
			return DAYS_IN_MONTH[month];
		} else {
			return (isLeapYear(year)) ? 29 : 28;
		}
	}
	
	/**
	 * Returns the number of days in a given year.
	 * 
	 * @param year year
	 * 
	 * @return the number of days in a given year
	 */
	public static int daysInYear(int year) {
		// COMPLETE THIS METHOD
		if(isLeapYear(year)) {
			return DAYS_LEAP_YEAR;
		}
		return DAYS_YEAR;
	}
	
	/**
	 * Returns the number of days in year of this date object.
	 * 
	 * @return the number of days in year of this date object
	 */
	public int daysInYear() {
		return SimpleDate.daysInYear(year);
	}
	
	/**
	 * Returns the day (SUNDAY, MONDAY, etc.) representing the day of the week for this date object.
	 * 
	 * @return the day of the week for this date object
	 */
	public DAY dayOfWeek() {
		int totalDays = 0;
		
		//gets total number of days since 1/1/1753
	    if(year > MIN_YEAR) {
	    	for(int i = MIN_YEAR; i < year; i++) {
	    		totalDays += daysInYear(i);
	    	}
	    }
	    totalDays += ordinalDate();
	    
	    //gets day of week enum index
	    int today = totalDays %7;
	    
	    return DAY.values()[today];
	}
	
	/**
	 * Returns the number of days elapsed (including this day) since January 1st.
	 * 
	 * @return the number of days elapsed (including this day) since January 1st
	 */
	public int ordinalDate() {
		int numDays = 0;
		
		if(SimpleDate.isValidDate(month, day, year)) {
			if(month == 1) {
				numDays = day;
			} else if(month == 2) {
				numDays = DAYS_THUS_FAR[2] + day;
			} else if(month > 2){
				numDays = DAYS_THUS_FAR[month];
				numDays += day;
				if(isLeapYear(year)) {
					numDays ++;
				}
			}
		}
		
		return numDays;
	}
	
	/**
	 * Returns a SimpleDate object representing the next date of this date object.
	 * 
	 * @return the next date of this date object
	 */
	public SimpleDate nextDate() {
		
		//year roll over
		if(month == 12 & day == 31) {
			return new SimpleDate(1,1, year + 1);
		}

		if(isValidDate(month, day + 1, year)) { 
			
			//just day forward
			return new SimpleDate(month, day + 1, year); 
		} else if(isValidDate(month + 1, 1, year)){ 
			
			//month roll over
			return new SimpleDate(month + 1, 1, year); 
		} else { 
			
			//year roll over
			return new SimpleDate(1,1, year + 1); 
		}
		
	}
	
	/**
	 * Returns a SimpleDate object representing the previous date of this date object. It returns
	 * SimpleDate(1,1,1753) if this date object represents the 1/1/1753.
	 * 
	 * @return the previous date of this date object
	 */
	public SimpleDate prevDate() {
		
		//checks for minimum date
		if(month == 1 && day == 1 && year == MIN_YEAR) {
			return new SimpleDate();
		}
		
		//for special leap year case
		if(isLeapYear(year) && month == 3 && day == 1) {
			return new SimpleDate(2,29,year);
		}
		if(isValidDate(month, day - 1, year)) {
			//back a day in same month
			return new SimpleDate(month, day -1 , year);
		}else if(day - 1 == 0 && month -1 != 0) {
			//back a month but not end of year
			return new SimpleDate(month -1, DAYS_IN_MONTH[month -1], year);
		}else if(month -1 == 0 && day - 1 == 0) {
			//back a year
			return new SimpleDate(12, 31, year -1);
		}else {
			return new SimpleDate(month, day, year);
		}
	}
	
	/**
	 * Returns a new SimpleDate object representing the date n days in future/past from this date object.
	 * If n &lt; 0, it returns the date n days in the past from this date object.
	 * If n &gt; 0, it returns the date n days in the future from this date object.
	 * If n = 0, it returns a a SimpleDate object whose field values are set to the field values of this date object.
	 * 
	 * @param n number of days in past (n &lt; 0) or future (n &gt; 0) from this date object
	 * 
	 * @return a SimpleDate object representing the date n days in future/past from this date object
	 */
	public SimpleDate dateFrom(int n) {
		SimpleDate date = new SimpleDate(month, day, year);

		//changing to a date in the past
		if(n < 0) {
			for(int i = 0; i > n; i--) {
				date = date.prevDate();
			}

		} 
		//changes to a date in the future
		if (n > 0) {
			for(int i = 0; i < n; i++){
				date = date.nextDate();
			}

		}
		
		return date;
	}
	
	/**
	 * Changes the month, day, and year field values of this object appropriately to advance the date
	 * by number of days specified by the argument.
	 * If n &gt; 0, the current date is advanced forward. If n &lt; 0, the current date is moved backward.
	 * If n = 0, the current date is unchanged.
	 * 
	 * @param n number of days to advance (forward or backward) the current date
	 */
	public void advance(int n) {
		SimpleDate date = dateFrom(n);

		//assigns to current SimpleDate object
		this.month = date.getMonth();
		this.day = date.getDay();
		this.year = date.getYear();

		new SimpleDate(this.month, this.day, this.year);
	}

	/**
	 * Returns the number of days between this date and the argument date (inclusive).
	 * 
	 * @param date date object to find the days between
	 * 
	 * @return the number of days between this date and the argument date (inclusive)
	 */
	public int daysBetween(SimpleDate date) {
		int days = 0;
		SimpleDate current = new SimpleDate(this.month, this.day, this.year);

		//if the day being compared is before the current
		if(current.compareTo(date) < 0) {
			while(!current.equals(date)) {
				days++;
				current.advance(1);
			}

		//if the day being compared is after the current
		}else if(current.compareTo(date) > 0) {
			while(!current.equals(date)) {
				days++;
				current.advance(-1);
			}
		}
		
		return days;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SimpleDate)) {
			return false;
		}
		SimpleDate other = (SimpleDate) obj;
		if (day != other.day) {
			return false;
		}
		if (month != other.month) {
			return false;
		}
		if (year != other.year) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return String.format("%s %d, %d", MONTH_NAMES[month], day, year);
	}

	@Override
	public int compareTo(SimpleDate other) {
		// COMPLETE THIS METHOD
		  if (this.year  < other.year) {
			  return -1;
		  }
	      if (this.year  > other.year) {
	    	  return +1;
	      }
	      if (this.month < other.month) {
	    	  return -1;
	      }
	      if (this.month > other.month) {
	    	  return +1;
	      }
	      if (this.day   < other.day) {
	    	  return -1;
	      }
	      if (this.day   > other.day) {
	    	  return +1;
	      }
		
		return 0;
	}
	
}