import pandas as pd
import pymysql.cursors

connection=pymysql.connect(host='112.169.196.210',port=10000,user='majin',password='1234',db='majin',charset='utf8',cursorclass=pymysql.cursors.DictCursor)
cursor=connection.cursor()
sql = 'select jkno,jconsecutivewinningp from jockeyskinny'
cursor.execute(sql)
result=cursor.fetchall()
result

df = pd.DataFrame(result)
df.columns
df.info
df.describe()

#df = df[df.jkno.str.contains('^[0-9]+$')]

df['jconsecutivewinningp']=df['jconsecutivewinningp'].str.replace(pat=" %",repl="")
df['jconsecutivewinningp']=df['jconsecutivewinningp'].apply(pd.to_numeric)

#df = df.astype('int')
df.to_csv('d:/data/final/jockey.csv',encoding='utf-8-sig')