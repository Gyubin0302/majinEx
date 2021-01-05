from urllib.request import urlopen
from bs4 import BeautifulSoup
import pandas as pd
import re
from datetime import datetime

columns=['meet','rcdate','rcno','rcdist','rank','wgbudam','chulno','hrname','hrno','name','age','sex','rating','jkname','jkno','sttime']

m_list=[]
for meet in range(1,4):
    for i in range(0,5):
        url="http://apis.data.go.kr/B551015/API26/entrySheet?ServiceKey=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D&pageNo=1&numOfRows=500&meet="+str(meet)+"&rc_month="+datetime.now().strftime('%Y%m')+"&rc_date="+str(int(datetime.now().strftime('%Y%m%d'))+i)
        print(url)
        html = urlopen(url)
        bsObj = BeautifulSoup(html, "html.parser")
        items = bsObj.select('item')
        if items:
            cnt = 0
            for item in items:
                if(item.meet and item.rcdate and item.rcno and item.rcdist and item.rank and item.wgbudam and item.chulno and item.hrname and item.hrno and item.prd and item.age and item.sex and item.rating and item.jkname and item.jkno and item.sttime):
                    list = []
                    cnt+=1
                    list.append(item.meet.text)
                    list.append(item.rcdate.text)
                    list.append(item.rcno.text)
                    list.append(item.rcdist.text)
                    list.append(item.rank.text)
                    list.append(item.wgbudam.text)
                    list.append(item.chulno.text)
                    list.append(item.hrname.text)
                    list.append(item.hrno.text)
                    list.append(item.prd.text)
                    list.append(item.age.text)
                    list.append(item.sex.text)
                    list.append(item.rating.text)
                    list.append(item.jkname.text)
                    list.append(item.jkno.text)
                    list.append(item.sttime.text[4:6]+item.sttime.text[7:9])
                    m_list.append(list)
            print(cnt)

pd = pd.DataFrame(m_list, columns=columns)
pd.to_csv('d:/data/final/real/horse_total_all_'+datetime.now().strftime('%Y%m%d')+'.csv',encoding='utf-8-sig')

#yundo data save csv