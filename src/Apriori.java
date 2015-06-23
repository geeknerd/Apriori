import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Apriori {

	private int support;
	private double inputSupport;
	private double confidence;
	private String fileName;
	private ArrayList<int[]> transactions;
	private int totalTransaction;
	private int[] attributes;
	private ArrayList<Integer> oneItemSet;
	private HashMap<ArrayList<Integer>, Integer> supportCount;
	private ArrayList<ArrayList<Integer>> frequentSet;
	private HashMap<String, Double> confidences;
	private String[] attributeNames;

	public static void main(String[] args) {
		Apriori apr = new Apriori();
		apr.fileName = args[0];
		apr.inputSupport = Double.parseDouble(args[1]);
		apr.confidence = Double.parseDouble(args[2]);
		try {
			apr.readFile(apr.fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		long minimumTime = System.nanoTime();
		apr.findMinimumSupport();
		long minimumEnd = System.nanoTime();
		System.out.println("\nFinding minimum support time: " + (double)(minimumEnd-minimumTime)/1000000000 + " seconds\n");
		
		long confidenceStart = System.nanoTime();
		apr.findConfidence();
		long confidenceEnd = System.nanoTime();
		System.out.println("\nFinding minimum confidence time: " + (double)(confidenceEnd-confidenceStart)/1000000000 + " seconds");
		
		System.out.println("\nTotal time for Apriori algorithm is: " + (double)(minimumEnd+confidenceEnd-minimumTime-confidenceStart)/1000000000 + " seconds\n");
	}

	public Apriori() {
		this.support = 0;
		this.confidence = 0;
		this.fileName = "";
		this.transactions = new ArrayList<>();
		this.totalTransaction = 0;
		this.attributes = new int[162];
		this.oneItemSet = new ArrayList<>();
		this.supportCount = new HashMap<>();
		this.frequentSet = new ArrayList<>();
		this.confidences = new HashMap<>();
		this.attributeNames = new String[]{ "ID=\'(-inf-147.8]\'", 
				"ID=\'(147.8-294.6]\'",
				"ID=\'(294.6-441.4]\'",
				"ID=\'(441.4-588.2]\'",
				"ID=\'(588.2-inf)\'",
				"Abbrev=Cra",
				"Abbrev=Cly", 
				"Abbrev=Nga",
				"Abbrev=Wai",
				"Abbrev=K81",
				"Abbrev=Wak",
				"Abbrev=K82",
				"Abbrev=WSp",
				"Abbrev=K83",
				"Abbrev=Lon",
				"Abbrev=Puk",
				"Abbrev=Paw",
				"Abbrev=K81a",
				"Abbrev=Mor",
				"Abbrev=Wen",
				"Abbrev=WSh",
				"Rep=\'(-inf-5.2]\'", 
				"Rep=\'(5.2-9.4]\'",
				"Rep=\'(9.4-13.6]\'",
				"Rep=\'(13.6-17.8]\'",
				"Rep=\'(17.8-inf)\'",
				"Locality=Central_Hawkes_Bay", 
				"Locality=Northern_Hawkes_Bay",
				"Locality=Southern_Hawkes_Bay",
				"Locality=Central_Hawkes_Bay_(coastal)", 
				"Locality=Central_Wairarapa",
				"Locality=South_Wairarapa",
				"Locality=Southern_Hawkes_Bay_(coastal)", 
				"Locality=Central_Poverty_Bay",
				"Map_Ref=N135_382/137",
				"Map_Ref=N116_848/985",
				"Map_Ref=N145_874/586",
				"Map_Ref=N142_377/957",
				"Map_Ref=N158_344/626",
				"Map_Ref=N162_081/300",
				"Map_Ref=N158_343/625",
				"Map_Ref=N151_912/221",
				"Map_Ref=N162_097/424",
				"Map_Ref=N166_063/197",
				"Map_Ref=N146_273/737",
				"Map_Ref=N141_295/063",
				"Map_Ref=N98_539/567",
				"Map_Ref=N151_922/226",
				"Latitude=39__38",
				"Latitude=39__00",
				"Latitude=40__11",
				"Latitude=39__50",
				"Latitude=40__57",
				"Latitude=41__12",
				"Latitude=40__36",
				"Latitude=41__08",
				"Latitude=41__16",
				"Latitude=40__00",
				"Latitude=39__43",
				"Latitude=82__32",
				"Altitude=\'(-inf-116]\'", 
				"Altitude=\'(116-162]\'",
				"Altitude=\'(162-208]\'",
				"Altitude=\'(208-254]\'",
				"Altitude=\'(254-inf)\'",
				"Rainfall=\'(-inf-940]\'",
				"Rainfall=\'(940-1030]\'",
				"Rainfall=\'(1030-1120]\'",
				"Rainfall=\'(1120-1210]\'",
				"Rainfall=\'(1210-inf)\'",
				"Frosts=\'(-inf--2.8]\'",
				"Frosts=\'(-2.8--2.6]\'",
				"Frosts=\'(-2.6--2.4]\'",
				"Frosts=\'(-2.4--2.2]\'",
				"Frosts=\'(-2.2-inf)\'",
				"Year=\'(-inf-1980.6]\'",
				"Year=\'(1980.6-1981.2]\'", 
				"Year=\'(1981.2-1981.8]\'",
				"Year=\'(1981.8-1982.4]\'",
				"Year=\'(1982.4-inf)\'",
				"Sp=co",
				"Sp=fr",
				"Sp=ma",
				"Sp=nd",
				"Sp=ni",
				"Sp=ob",
				"Sp=ov",
				"Sp=pu",
				"Sp=rd",
				"Sp=si",
				"Sp=mn",
				"Sp=ag",
				"Sp=bxs",
				"Sp=br",
				"Sp=el",
				"Sp=fa",
				"Sp=jo",
				"Sp=ka",
				"Sp=re",
				"Sp=sm",
				"Sp=ro",
				"Sp=nc",
				"Sp=am",
				"Sp=cr",
				"Sp=pa",
				"Sp=ra",
				"Sp=te",
				"PMCno=\'(-inf-551]\'", 
				"PMCno=\'(551-1101]\'",
				"PMCno=\'(1101-1651]\'", 
				"PMCno=\'(1651-2201]\'",
				"PMCno=\'(2201-inf)\'",
				"DBH=\'(-inf-8417.464]\'", 
				"DBH=\'(8417.464-16834.348]\'", 
				"DBH=\'(16834.348-25251.232]\'", 
				"DBH=\'(25251.232-33668.116]\'",
				"DBH=\'(33668.116-inf)\'",
				"Ht=\'(-inf-5.254]\'", 
				"Ht=\'(5.254-9.388]\'", 
				"Ht=\'(9.388-13.522]\'", 
				"Ht=\'(13.522-17.656]\'", 
				"Ht=\'(17.656-inf)\'",
				"Surv=\'(-inf-21.2]\'", 
				"Surv=\'(21.2-40.9]\'",
				"Surv=\'(40.9-60.6]\'",
				"Surv=\'(60.6-80.3]\'",
				"Surv=\'(80.3-inf)\'",
				"Vig=\'(-inf-1.4]\'",
				"Vig=\'(1.4-2.3]\'",
				"Vig=\'(2.3-3.2]\'",
				"Vig=\'(3.2-4.1]\'",
				"Vig=\'(4.1-inf)\'",
				"Ins_res=\'(-inf-0.9]\'", 
				"Ins_res=\'(0.9-1.8]\'",
				"Ins_res=\'(1.8-2.7]\'",
				"Ins_res=\'(2.7-3.6]\'",
				"Ins_res=\'(3.6-inf)\'",
				"Stem_Fm=\'(-inf-1]\'",
				"Stem_Fm=\'(1-2]\'",
				"Stem_Fm=\'(2-3]\'",
				"Stem_Fm=\'(3-4]\'",
				"Stem_Fm=\'(4-inf)\'", 
				"Crown_Fm=\'(-inf-1]\'", 
				"Crown_Fm=\'(1-2]\'",
				"Crown_Fm=\'(2-3]\'",
				"Crown_Fm=\'(3-4]\'",
				"Crown_Fm=\'(4-inf)\'", 
				"Brnch_Fm=\'(-inf-1]\'", 
				"Brnch_Fm=\'(1-2]\'",
				"Brnch_Fm=\'(2-3]\'",
				"Brnch_Fm=\'(3-4]\'",
				"Brnch_Fm=\'(4-inf)\'", 
				"Utility=none",
				"Utility=low",
				"Utility=average", 
				"Utility=good",
				"Utility=best",
				"LogRainfall=\'(-inf-6.830213]\'", 
				"LogRainfall=\'(6.830213-6.91519]\'", 
				"LogRainfall=\'(6.91519-7.000166]\'",
				"LogRainfall=\'(7.000166-7.085143]\'", 
		"LogRainfall=\'(7.085143-inf)\'" };
	}

	public void readFile(String fileName) throws IOException{
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String nextLine;
			while((nextLine = br.readLine()) != null){
				int[] transaction = new int[162];
				String[] data = nextLine.split(",");
				for(int i = 0; i < 162; i++){
					transaction[i] = Integer.parseInt(data[i]);
				}
				this.transactions.add(transaction);
				this.totalTransaction ++;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.out.println("Apriori");
		System.out.println("================");
		this.support = (int) (this.totalTransaction * this.inputSupport);
		System.out.println("Total transaction: " + this.totalTransaction);
		System.out.println("Minimum support: " +this.inputSupport + " ("+ this.support + " instances)");
		System.out.println("Minimum confidence: " + this.confidence);
		System.out.println();
	}//end of readFile

	public void findMinimumSupport() {
		for(int[] t : transactions){
			for(int i=0; i < 162; i++){
				if(t[i] ==1){
					attributes[i] ++;
				}
			}
		}

		for (int i = 0; i < 162; i++){
			if(attributes[i] >= this.support){
				ArrayList<Integer> toMap = new ArrayList<>();
				toMap.add(i+1);
				this.supportCount.put(toMap, attributes[i]);
				this.frequentSet.add(toMap);
				this.oneItemSet.add(i+1);
			}
		}
		System.out.println("1 item set:");
		for(ArrayList<Integer> oneItem : this.frequentSet){
			if(oneItem.size() == 1){
				System.out.println(this.attributeNames[oneItem.get(0)-1] + " " + this.supportCount.get(oneItem));
			}
		}
		System.out.println();
		ArrayList<ArrayList<Integer>> twoItemSet = new ArrayList<>();
		System.out.println("2 item set: ");
		for (int i = 0; i < this.oneItemSet.size()-1; i++){
			for(int j = i + 1; j < this.oneItemSet.size(); j++){
				ArrayList<Integer> temp = new ArrayList<>();
				temp.add(this.oneItemSet.get(i));
				temp.add(this.oneItemSet.get(j));
				int count = 0;
				for(int[] transaction : this.transactions){
					if(transaction[temp.get(0)-1] == 1 && transaction[temp.get(1)-1] == 1){
						count ++;
					}
				}
				if (count > this.support){
					twoItemSet.add(temp);
					this.frequentSet.add(temp);
					System.out.println(this.attributeNames[temp.get(0)-1]+ "  " + this.attributeNames[temp.get(1)-1] + " " + count);
					this.supportCount.put(temp, count);
				}
			}
		}

		ArrayList<ArrayList<Integer>> forNext = twoItemSet;
		int itemSetNumber = 2;
		while(forNext.size() > 0){

			ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
			boolean add = true;
			for (int i = 0; i < forNext.size()-1; i++) {
				for (int j = i+1; j < forNext.size(); j++){
					ArrayList<Integer> toBeAdded = new ArrayList<>();
					ArrayList<Integer> listI = forNext.get(i);
					ArrayList<Integer> listJ = forNext.get(j);
					int containNumber = 0;
					for(Integer value : listJ){
						if(listI.contains(value)){
							containNumber++;
						}
					}
					if(containNumber == (listI.size()-1)){
						for(Integer value : listI){
							toBeAdded.add(value);
						}
						for(Integer value : listJ){
							if(!toBeAdded.contains(value)){
								toBeAdded.add(value);
							}
						}
					}
					Collections.sort(toBeAdded);
					int count = 0;
					for(int[] transaction : this.transactions){
						for(Integer value : toBeAdded){
							if(transaction[value-1] == 0){
								add = false;
							}
						}

						if(add){
							count ++;
						}
						add = true;
					}
					if(toBeAdded.size()>0 && !temp.contains(toBeAdded) && count >= this.support){
						this.frequentSet.add(toBeAdded);
						this.supportCount.put(toBeAdded, count);
						temp.add(toBeAdded);
					}
				}
			}
			if(temp.size() >0){
				System.out.println();
				itemSetNumber ++;
				System.out.println(itemSetNumber+" item set :" );
				for(ArrayList<Integer> t : temp){
					String output = "";
					for(Integer i : t){
						output += this.attributeNames[i-1] + " ";
					}
					System.out.println(output + " " + this.supportCount.get(t));
				}
			}
			forNext = temp;
		}
		System.out.println();
	}//end of findMinimumSupport

	public void findConfidence() {
		for(int i = 0; i < this.frequentSet.size()-1; i++){
			for(int j = i+1; j < this.frequentSet.size(); j++){
				if(this.frequentSet.get(i).size() < this.frequentSet.get(j).size()){
					boolean process = true;
					for (Integer value : this.frequentSet.get(i)){
						if(!this.frequentSet.get(j).contains(value)){
							process = false;
						}
					}

					if(process){
						ArrayList<Integer> key = new ArrayList<>();
						ArrayList<Integer> value = new ArrayList<>();
						for (Integer item : this.frequentSet.get(i)){
							key.add(item);
						}
						for (Integer item : this.frequentSet.get(j)){
							if(!key.contains(item)){
								value.add(item);
							}
						}
						String mapping = "";
						for(Integer keyName: key){
							mapping += this.attributeNames[keyName-1];
						}
						mapping += " ==> ";
						for(Integer valueValue: value){
							mapping += this.attributeNames[valueValue-1];
						}
						if(this.supportCount.containsKey(this.frequentSet.get(j)) && this.supportCount.containsKey(key)){
							this.confidences.put(mapping, (double)this.supportCount.get(this.frequentSet.get(j))/this.supportCount.get(key));
						}
					}//end of process
				}
			}
		}

		System.out.println("Association Rules with Confidence: "); 
		System.out.println();
		ValueComparator bvc = new ValueComparator(this.confidences);
		TreeMap<String, Double> sorted_confidences = new TreeMap<String, Double>(bvc);
		sorted_confidences.putAll(confidences);
		for(String key : sorted_confidences.keySet()){
			if(confidences.get(key)>=this.confidence){
				System.out.println(key + "   conf:(" + confidences.get(key) + ")");
			}
		}
	}
}

class ValueComparator implements Comparator<String>{

	Map<String, Double> base;
	public ValueComparator(Map<String,Double> base){
		this.base = base;
	}
	@Override
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)){
			return -1;
		}else{
			return 1;
		}
	}

}
