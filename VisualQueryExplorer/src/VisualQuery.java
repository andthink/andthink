
import java.io.*;
import java.nio.charset.Charset;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author SCOCRI
 */
public class VisualQuery {

	private VQTable[] Table;
	private VQField[] Field;
	private VQJoin[] Join;
	private VQFilter[] Filter;
	private VQOrderBy[] OrderBy;
	private VQGroupBy[] GroupBy;
	private VQFilterParameter[] FilterParameter;

	VisualQuery() {
	}

	public VQTable[] getTable() {
		return Table;
	}

	public void setTable(VQTable[] Table) {
		this.Table = Table;
	}

	public VQField[] getField() {
		return Field;
	}

	public void setField(VQField[] Field) {
		this.Field = Field;
	}

	public VQFilter[] getFilter() {
		return Filter;
	}

	public void setFilter(VQFilter[] Filter) {
		this.Filter = Filter;
	}

	public VQOrderBy[] getOrderBy() {
		return OrderBy;
	}

	public void setOrderBy(VQOrderBy[] OrderBy) {
		this.OrderBy = OrderBy;
	}

	public VQGroupBy[] getGroupBy() {
		return GroupBy;
	}

	public void setGroupBy(VQGroupBy[] GroupBy) {
		this.GroupBy = GroupBy;
	}
	
	public VQFilterParameter[] getFilterParameter() {
		return FilterParameter;
	}

	public void setFilterParameter(VQFilterParameter[] FilterParameter) {
		this.FilterParameter = FilterParameter;
	}

	public VQJoin[] getJoin() {
		return Join;
	}

	public void setJoin(VQJoin[] Join) {
		this.Join = Join;
	}

	private String formatString(String str) {
		return str.replace("\"", "");
	}

	private String formatNumber(String str) {
		return str.replace(" ", "");
	}

	public void composeVQ(String path) {
		try {
			FileInputStream fstream = new FileInputStream(path);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "ISO-8859-1"));

			//Table
			int nTable = Integer.parseInt(this.formatNumber(br.readLine()));
			this.Table = new VQTable[nTable];

			for (int i = 0; i < nTable; i++) {
				String Description = this.formatString(br.readLine());
				String Alias = this.formatString(br.readLine());
				String TableName = this.formatString(br.readLine());

				this.Table[i] = new VQTable(Description, Alias, TableName, "");

				//Salto le righe non gestite e quella con l'asterisco
				for (int j = 0; j < 3; j++) {
					br.readLine();
				}
			}

			//Field
			int nField = Integer.parseInt(this.formatNumber(br.readLine()));
			this.Field = new VQField[nField];

			for (int i = 0; i < nField; i++) {
				String Description = this.formatString(br.readLine());
				String Name = this.formatString(br.readLine());
				String Alias = this.formatString(br.readLine());
				String Type = this.formatString(br.readLine());
				String Len = this.formatNumber(br.readLine());
				String Dec = this.formatNumber(br.readLine());

				this.Field[i] = new VQField(Description, Name, Alias, Type, Len, Dec);

				//Salto la riga con l'asterisco
				br.readLine();
			}

			//Join
			int nJoin = Integer.parseInt(this.formatNumber(br.readLine()));
			this.Join = new VQJoin[nJoin];

			for (int i = 0; i < nJoin; i++) {
				String Description = this.formatString(br.readLine());
				String Expression = this.formatString(br.readLine());
				String Type = this.formatString(br.readLine());
				String Table1 = this.formatString(br.readLine());
				String Table2 = this.formatString(br.readLine());

				this.Join[i] = new VQJoin(Description, Expression, Type, Table1, Table2);

				//Salto la riga con l'asterisco
				br.readLine();
			}

			//Filter
			int nFilter = Integer.parseInt(this.formatNumber(br.readLine()));
			this.Filter = new VQFilter[nFilter];

			for (int i = 0; i < nFilter; i++) {
				String FieldName = this.formatString(br.readLine());
				String Not = this.formatString(br.readLine());
				String Criteria = this.formatString(br.readLine());
				String Example = this.formatString(br.readLine());
				String Operator = this.formatString(br.readLine());
				String Having = this.formatString(br.readLine());

				this.Filter[i] = new VQFilter(FieldName, Not, Criteria, Example, Operator, Having);

				//Salto la riga con l'asterisco
				br.readLine();
			}

			//OrderBy
			int nOrderBy = Integer.parseInt(this.formatNumber(br.readLine()));
			this.OrderBy = new VQOrderBy[nOrderBy];

			for (int i = 0; i < nOrderBy; i++) {
				String Description = this.formatString(br.readLine());
				String FieldName = this.formatString(br.readLine());
				String Cardinality = this.formatString(br.readLine());

				this.OrderBy[i] = new VQOrderBy(Description, FieldName, Cardinality);

				//Salto la riga con l'asterisco
				br.readLine();
			}
			
			//GroupBy
			int nGroupBy = Integer.parseInt(this.formatNumber(br.readLine()));
			this.GroupBy = new VQGroupBy[nGroupBy];

			for (int i = 0; i < nGroupBy; i++) {
				String Description = this.formatString(br.readLine());
				String FieldName = this.formatString(br.readLine());

				this.GroupBy[i] = new VQGroupBy(Description, FieldName);

				//Salto la riga con l'asterisco
				br.readLine();
			}
			
			//Salto 1 righe misteriose
			for (int j = 0; j < 1; j++) {
				br.readLine();
			}
			
			//FilterParameter
			int nFilterParameter = Integer.parseInt(this.formatNumber(br.readLine()));
			this.FilterParameter = new VQFilterParameter[nFilterParameter];

			for (int i = 0; i < nFilterParameter; i++) {
				String FieldName = this.formatString(br.readLine());
				String Description = this.formatString(br.readLine());
				String Type = this.formatString(br.readLine());
				String Len = this.formatString(br.readLine());
				String Dec = this.formatString(br.readLine());
				String RoE = this.formatString(br.readLine());

				this.FilterParameter[i] = new VQFilterParameter(FieldName, Description, Type, Len, Dec, RoE);

				//Salto la riga con l'asterisco
				br.readLine();
			}

			fstream.close();
			in.close();
			br.close();
		} catch (FileNotFoundException fnfeex) {
			this.Table = new VQTable[0];
			this.Field = new VQField[0];
			this.Join = new VQJoin[0];			
			this.Filter = new VQFilter[0];			
			this.FilterParameter = new VQFilterParameter[0];
			this.OrderBy = new VQOrderBy[0];			
			this.GroupBy = new VQGroupBy[0];
		} catch (IOException | NullPointerException | NumberFormatException ex) {
			this.Table = new VQTable[0];
			this.Field = new VQField[0];
			this.Join = new VQJoin[0];			
			this.Filter = new VQFilter[0];			
			this.FilterParameter = new VQFilterParameter[0];
			this.OrderBy = new VQOrderBy[0];			
			this.GroupBy = new VQGroupBy[0];
		}
	}
}
