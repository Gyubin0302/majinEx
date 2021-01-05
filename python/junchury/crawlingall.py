from urllib.request import urlopen
from bs4 import BeautifulSoup
import pandas as pd
import re

columns=['meet','rcdate','rcday','rcno','rcdist','rank','wgbudam','weather','track','ord','chulno','hrname','hrno','name','age','sex','rating','jkname','jkno','trname','trno','rctime','wghr','diffunit','winodds','plcodds']

year=2021
m_list=[]
for meet in range(1,4):
    for i in range(1,100):
        url="http://apis.data.go.kr/B551015/API4/raceResult?ServiceKey=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D&pageNo=1&numOfRows=100000&meet="
        url+=str(meet)+"&rc_year="+str(year-i)
        #url="http://apis.data.go.kr/B551015/API4/raceResult?ServiceKey=YsfeAQ1K0KPH1fOqYRLcvfqOP2P6Mo2iQOiZSumF4bMSlfyWjdPg4NWPu7Y5ms%2Fql9n2oi4dQbNq2bISj%2Bi4Hg%3D%3D&pageNo=1&numOfRows=10&meet=1&year=2019"
        print(url)
        html = urlopen(url)
        bsObj = BeautifulSoup(html, "html.parser")
        items = bsObj.select('item')
        if items:
            cnt = 0
            for item in items:
                if(item.meet and item.rcdate and item.rcday and item.rcno and item.rcdist and item.rank and item.wgbudam and item.weather and item.track and item.ord and item.chulno and item.hrname and item.hrno and item.name and item.age and item.sex and item.rating and item.jkname and item.jkno and item.trname and item.trno and item.rctime and item.wghr and item.diffunit and item.winodds and item.plcodds):
                    list = []
                    cnt+=1
                    list.append(item.meet.text)
                    list.append(item.rcdate.text)
                    list.append(item.rcday.text)
                    list.append(item.rcno.text)
                    list.append(item.rcdist.text)
                    list.append(item.rank.text)
                    list.append(item.wgbudam.text)
                    list.append(item.weather.text)
                    track=re.search("(\/>).+?(<)", str(item))
                    list.append(track[0][2:-1])
                    list.append(item.ord.text)
                    list.append(item.chulno.text)
                    list.append(item.hrname.text)
                    list.append(item.hrno.text)
                    list.append(str(item.select('name'))[7:-8])
                    list.append(item.age.text)
                    list.append(item.sex.text)
                    list.append(item.rating.text)
                    list.append(item.jkname.text)
                    list.append(item.jkno.text)
                    list.append(item.trname.text)
                    list.append(item.trno.text)
                    list.append(item.rctime.text)
                    list.append(item.wghr.text)
                    list.append(item.diffunit.text)
                    list.append(item.winodds.text)
                    list.append(item.plcodds.text)
                    m_list.append(list)
            print(cnt)
        else:
            break

pd = pd.DataFrame(m_list, columns=columns)
pd.to_csv('d:/data/fianl/horse_total_all.csv',encoding='utf-8-sig')

#yundo data save csv