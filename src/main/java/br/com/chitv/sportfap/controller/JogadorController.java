package br.com.chitv.sportfap.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.context.RequestContext;

import br.com.chitv.sportfap.dao.JogadorDao;
import br.com.chitv.sportfap.model.Jogador;


@Named
@RequestScoped
public class JogadorController implements Serializable {

	private static final long serialVersionUID = 5202799275364383072L;

	private Jogador jogador;

	private static List<Jogador> listaJogadores;

	private List<Jogador> listaJogadoresTmp = new ArrayList<Jogador>();
	
	public List<Jogador> getListaJogadoresTmp() {
		return listaJogadoresTmp;
	}

	public void setListaJogadoresTmp(Jogador jogador) {
		listaJogadoresTmp.add(jogador);
	}

	@Inject
	private JogadorDao jogadorDao;

	@PostConstruct
	public void init() {
		this.listaJogadores = jogadorDao.listaJogadores();	
		this.jogador = new Jogador();
	}

	public List<Jogador> getJogadores() {
		if (this.listaJogadores == null || this.listaJogadores.isEmpty()) {
			this.listaJogadores = this.jogadorDao.listaJogadores();
		}
		return this.listaJogadores;
	}


	public String excluir() {
		this.jogadorDao.excluir(this.jogador);
		this.listaJogadores = jogadorDao.listaJogadores();
		this.jogador = new Jogador();
		return "index?faces-redirect=true";
	}

	public String irPaginaAtualizar(Jogador jogador) {
		this.jogador = jogador;
		return "jogador/atualizar";
	}

	public String irPaginaSalvar() {
		return "jogador/cadastrar?faces-redirect=true";
	}
	public String irPaginaLogin() {
		return "../secured/views/login.xhtml";
	}

	@Transactional
	public String atualizar() {
		this.jogadorDao.atualizar(this.jogador);
		this.jogador = new Jogador();
		return "../index.xhtml";
	}
	public String irPaginaJogadores() {
		return "../secured/views/admin/index.xhtml";
	}

	public String salvar() {
		this.jogadorDao.salvar(this.jogador);
		this.jogador = new Jogador();
		this.listaJogadores = jogadorDao.listaJogadores();		
		
		this.listaJogadoresTmp.add(this.jogador);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Jogador Cadastrado Com Sucesso!"));
		 RequestContext.getCurrentInstance().reset("cadastrarTimeForm:panel");
		 return "cadastrar?faces-redirect=true";
	}

	public String voltar() {
		return "/secured/views/admin/index?faces-redirect=true";
	}

	public List<Jogador> getlistaJogadores() {
		return listaJogadores;
	}

	public void setlistaJogadores(List<Jogador> listaJogadores) {
		this.listaJogadores = listaJogadores;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}
	
}