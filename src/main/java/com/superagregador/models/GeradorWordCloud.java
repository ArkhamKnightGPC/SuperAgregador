package com.superagregador.models;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.filter.Filter;
import com.kennycason.kumo.palette.ColorPalette;

public class GeradorWordCloud {
	List<String> palavras = new ArrayList<>();
	int idUsuario;
	
	public GeradorWordCloud(List<String> palavras, int idUsuario) throws Exception {
		this.palavras = palavras;
		this.idUsuario = idUsuario;
		//vamos escrever no txt
		FileOutputStream fos = new FileOutputStream("src/main/resources/textoWordCloud.txt");
		for(int i=0; i<palavras.size(); i++) {
			String palavra = palavras.get(i)+"\n";
			byte[] b = palavra.getBytes();
			fos.write(b); fos.flush();
		}
		fos.close();
	}
	
	public void gerarWordCloud() throws IOException {
		FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("src/main/resources/textoWordCloud.txt");
		Dimension dimension = new Dimension(600, 600);
		WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(300));
		wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(10, 40));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("src/main/resources/static/img/WordCloud" + idUsuario +".png");
	}
}

