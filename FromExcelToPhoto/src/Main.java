import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Main {

	public static Image imageto;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		
		final JFrame parent = new JFrame();
        JButton button = new JButton();

        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        
        
        String fileName = JOptionPane.showInputDialog(parent,
                "Input the location of the Excel File:", null);
        parent.setVisible(false);
        
        onStartButtonClicked(fileName);
		
//		JFrame input = new JFrame();
//		JTextField fileName = new JTextField();
//		JLabel inputSpecifications = new JLabel("Input the location of the Excel File:");
//		JButton submitButton = new JButton("Submit");
//		submitButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent evt) {
//                try {
//					onStartButtonClicked();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            }
//		});
//		submitButton.setSize(10, 10);
//		
//		
//		input.add(inputSpecifications);
//		input.add(fileName);
//		input.add(submitButton);
//		input.setSize(500, 500);
//		
//		
//		//input.pack();
//		input.setVisible(true);
		
		
//		int[][] pixels = check();
//
//		JFrame jf = new JFrame();
//		JLabel jl = new JLabel();
//
//		// 3 bands in TYPE_INT_RGB
//		int NUM_BANDS = 3;
//		int[] arrayimage = new int[pixels.length * pixels[0].length * NUM_BANDS]; // change 28 to width
//															// and height
//
//		for (int i = 0; i < pixels.length; i+=3) {
//			for (int j = 0; j < pixels[0].length; j++) {
//				for (int band = 0; band < NUM_BANDS; band++){
//					
//					arrayimage[((j * pixels.length) + i) + band] = pixels[i+band][j];
//					//arrayimage[((i * pixels[0].length) + j) * NUM_BANDS + band] = pixels[j+band][i];
//
//				}
//
//				
//				// make
//																					// it
//																					// the
//																					// actual
//																					// pixel
//																					// colour
//																					// (R,
//																					// G,
//																					// B)
//			}
//		}
//		ImageIcon ii = new ImageIcon(getImageFromArray(arrayimage, pixels.length/3, pixels[0].length));
//		jl.setIcon(ii);
//		jf.add(jl);
//		jf.pack();
//		jf.setVisible(true);
	}
	
	public static void onStartButtonClicked(String fileName) throws IOException{
		int[][] pixels = check(fileName);

		JFrame jf = new JFrame();
		JLabel jl = new JLabel();

		// 3 bands in TYPE_INT_RGB
		int NUM_BANDS = 3;
		int[] arrayimage = new int[pixels.length * pixels[0].length * NUM_BANDS]; // change 28 to width
															// and height

		for (int i = 0; i < pixels.length; i+=3) {
			for (int j = 0; j < pixels[0].length; j++) {
				for (int band = 0; band < NUM_BANDS; band++){
					
					arrayimage[((j * pixels.length) + i) + band] = pixels[i+band][j];
					//arrayimage[((i * pixels[0].length) + j) * NUM_BANDS + band] = pixels[j+band][i];

				}

				
				// make
																					// it
																					// the
																					// actual
																					// pixel
																					// colour
																					// (R,
																					// G,
																					// B)
			}
		}
		ImageIcon ii = new ImageIcon(getImageFromArray(arrayimage, pixels.length/3, pixels[0].length));
		jl.setIcon(ii);
		jf.add(jl);
		jf.pack();
		jf.setVisible(true);
	}

	public static Image getImageFromArray(int[] pixels, int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		WritableRaster raster = (WritableRaster) image.getData();
		raster.setPixels(0, 0, width, height, pixels);
		image.setData(raster);
		return image;
	}

	public static void loadPixels(JLabel label, int[][] pixels) {
		// 1. convert to 1-d array
		int width = pixels.length;
		int height = pixels[0].length;
		int i = 0; // index into new pixarray
		int[] pixarray = new int[width * height];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				// these are stored y-major!
				pixarray[i++] = pixels[x][y];
		// 2. convert 1-d array into Image*/
		MemoryImageSource source = new MemoryImageSource(width, height,
				pixarray, 0, width);

		imageto = label.createImage(source); // imageto is declared in the top
												// of the class as
												// "private static Image"

		// 3. store Image in the JLabel
		ImageIcon imic = new ImageIcon(imageto);

		// imic=image;
		label.setIcon(imic);
	}


	public static int[][] check(String fileName) throws IOException {
		Scanner sc = new Scanner(System.in);
		//need to input double slashes
		//String filename = "C:\\Users\\jackw\\Desktop\\PhysicsCode.xlsx";
		//String filename = "C:\\Users\\jackw\\Desktop\\Lee - RGB Values.xlsx";
		//String filename = "C:\\Users\\jackw\\Desktop\\cyan.xlsx";
		
		File file = new File(fileName);
		
		FileInputStream input = new FileInputStream(file);
		
		
		XSSFWorkbook excelFile = new XSSFWorkbook(input);
		
		XSSFSheet s = excelFile.getSheetAt(0);
		
		ArrayList<ArrayList> rows = new ArrayList<ArrayList>();
		Iterator<Row> rowIterator = s.iterator();
		while(rowIterator.hasNext()){
			//System.out.println("shits going down");
			
			Row row = rowIterator.next();
			ArrayList<Integer> cols = new ArrayList<Integer>();
			
			Iterator<Cell> cellIterator = row.cellIterator();
			while(cellIterator.hasNext()){
				//System.out.println("even more shits going down");
				Cell cell = cellIterator.next();
				
				switch(cell.getCellType()){
				case Cell.CELL_TYPE_NUMERIC:
					cols.add((int)cell.getNumericCellValue());
				}
			}
			rows.add(cols);
		}
//		System.out.println(rows.get(1).get(0));
//		System.out.println(rows.get(1).get(1));
//		System.out.println(rows.get(1).get(2));
//		System.out.println(rows.get(1).get(3));
		
		int width = (int) rows.get(0).get(3);
		int height = (rows.size())/width;
		
		//System.out.println(width);
		
		//Pixel[][] picture = new Pixel[width][height];
		
		//May need to make this a method in a picture class
//		for(int i = 1, count = 0, h = 0, w = 0; i < rows.size(); i++, count++, w++){
//			if(count >= width){
//				h++;
//				count = 0;
//				w = 0;
//			}
//			Pixel pix = new Pixel(w,h);
//			pix.setAlpha((int) rows.get(i).get(0));
//			pix.setRed((int) rows.get(i).get(1));
//			pix.setGreen((int) rows.get(i).get(2));
//			pix.setBlue((int) rows.get(i).get(3));
//			picture[w][h] = pix;
//		}
		
		int[][] picture = new int[width*3][height];
		for(int i = 0, count = 0, h = 0, w = 0; i < rows.size(); i++, count+=3, w+=3){
			if(count >= width*3){
				h++;
				count = 0;
				w = 0;
			}
			//System.out.println(i);
			//Set R
			picture[w][h] = (int) rows.get(i).get(0);
			//Set G
			picture[w+1][h] = (int) rows.get(i).get(1);
			//Set B
			picture[w+2][h] = (int) rows.get(i).get(2);
			
		}
		
		//System.out.println(picture[4][0]);
		input.close();
		excelFile.close();
		return picture;		
		
				
		
	}
}