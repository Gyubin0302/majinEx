import pymysql.cursors
from sqlalchemy import create_engine
import pandas as pd
pymysql.install_as_MySQLdb()
import MySQLdb

df = pd.read_csv('d:/data/final/real/horse_total_merged.csv')
del df['Unnamed: 0']
df.reindex()
df.index.name='idx'
df
#connection=pymysql.connect(host='112.169.196.210',port=10000,user='majin',password='1234',db='majin',charset='utf8',cursorclass=pymysql.cursors.DictCursor)

engine = create_engine("mysql+mysqldb://majin:"+"1234"+"@112.169.196.210:10000/majin", encoding='utf-8')
conn = engine.connect()

df.to_sql(name="race", con=engine, if_exists='append')