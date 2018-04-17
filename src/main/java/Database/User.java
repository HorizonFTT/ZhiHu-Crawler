package Database;

/**
 * User
 */
public class User {
	private String id;

	private String name;

	private String sex;

	private String introduction;

	private String school;

	private String company;

	private String job;

	private String business;

	private String location;

	private int answer;

	private int agree;

	private int follower;

	private int coverInt(String s) {
		if (s == null)
			return 0;
		String[] temp = s.split(",");//for css selector
		int sum = 0;
		int base = 0;
		for (int i = 0; i < temp.length; i++) {
			base = (int) Math.pow(1000, temp.length - i - 1);
			sum += base * Integer.valueOf(temp[i]);
		}
		return sum;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the introduction
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @param introduction the introduction to set
	 */
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	/**
	 * @return the answer
	 */
	public int getAnswer() {
		return answer;
	}

	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = coverInt(answer);
	}

	/**
	 * @return the agree
	 */
	public int getAgree() {
		return agree;
	}

	/**
	 * @param agree the agree to set
	 */
	public void setAgree(String agree) {
		this.agree = coverInt(agree);
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the follower
	 */
	public int getFollower() {
		return follower;
	}

	/**
	 * @param follower the follower to set
	 */
	public void setFollower(String follower) {
		this.follower = coverInt(follower);
	}

	/**
	 * @return the school
	 */
	public String getSchool() {
		return school;
	}

	/**
	 * @param school the school to set
	 */
	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the job
	 */
	public String getJob() {
		return job;
	}

	/**
	 * @param job the job to set
	 */
	public void setJob(String job) {
		this.job = job;
	}

	/**
	 * @return the business
	 */
	public String getBusiness() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(String business) {
		this.business = business;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

}