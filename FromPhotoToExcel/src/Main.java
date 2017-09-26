import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		final JFrame parent = new JFrame();
        JButton button = new JButton();

        parent.add(button);
        parent.pack();
        parent.setVisible(true);

        
        
        String photoFileName = JOptionPane.showInputDialog(parent,
                "Input the location (including the file's name and extension) of the photo you wish to save as an excel:", null);
        
        String excelFileName = JOptionPane.showInputDialog(parent,
                "Input the location of where you want the excel to be saved:", null);
        
        parent.setVisible(false);
        
        
        makeExcel(photoFileName, excelFileName);
        
		
	}
	
	public static void makeExcel(String photoFileName, String excelFileName){
		//excelFileName = "C:\\Users\\jackw\\Desktop\\test11.xlsx";
		//photoFileName = "C:\\Users\\jackw\\Desktop\\hunteranddeer.jpg";
		
		
		
		FileInputStream file = null;
		//File mainFile = null;
		try {
			file = new FileInputStream(photoFileName);
			//mainFile = new File(photoFileName);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//File file = new File(filename);
		
		int width = image.getWidth();
		//System.out.println(image.getWidth());
		//System.out.println(image.getHeight());
		int[][] picture = getPix.convertTo2DWithoutUsingGetRGB(image);
		//int[][] picture = new int[width*3][height];
		
		XSSFWorkbook excelFile = new XSSFWorkbook();
		
		XSSFSheet sheet = excelFile.createSheet("Pixels");
		
		Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
		for(int i = 0; i < picture.length; i++){
			for(int j = 0, k = 0; j < picture[i].length; j+=3, k++){
				//ADD IF STATEMENT FOR WIDTH
				if(i == 0 && j==0){
					data.put(k+1 + i*width, new Object[] {picture[i][j],picture[i][j+1],picture[i][j+2],width});
				}
				else{
					data.put(k+1 + i*width, new Object[] {picture[i][j],picture[i][j+1],picture[i][j+2]});
				}
			}
		}
		
		Set<Integer> keyset = data.keySet();
        int rownum = 0;
        for (Integer key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(excelFileName));
            excelFile.write(out);
            out.close();
            excelFile.close();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
	}

}
