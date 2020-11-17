#-*- coding: utf-8 -*-
import sys
import re
from lxml import etree
import urllib.request
import zipfile
from nltk.tokenize import word_tokenize, sent_tokenize
import pandas as pd
import matplotlib.pyplot as plt
from gensim.models.word2vec import Word2Vec
from konlpy.tag import Okt


def w2v(search, textFile):
    path = "C:/Users/rbsks/dl4j-examples-data/dl4j-examples/nlp/"+textFile
    train_data = pd.read_table(open(path, mode="r", encoding="UTF-8"))

    # Null 값이 존재하는 행 제거
    train_data = train_data.dropna(how = 'any')

    # 정규 표현식을 통한 한글 외 문자 제거
    train_data["마명"] = train_data["마명"].str.replace("[^ㄱ-ㅎㅏ-ㅣ가-힣 ]","")


    # 불용어 정의
    stopwords = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']

    okt = Okt()
    tokenized_data = []
    for sentence in train_data['마명']:
        temp_X = okt.morphs(sentence, stem=True) # 토큰화
        temp_X = [word for word in temp_X if not word in stopwords] # 불용어 제거
        tokenized_data.append(temp_X)

    model = Word2Vec(sentences = tokenized_data, size = 100, window = 5, min_count = 5, workers = 4, sg = 0)

    # 완성된 임베딩 매트릭스의 크기 확인
    print(model.wv.vectors.shape)
    print(model.wv.most_similar(search))

search = sys.argv[1]
textFile = sys.argv[2]
w2v(search, textFile)