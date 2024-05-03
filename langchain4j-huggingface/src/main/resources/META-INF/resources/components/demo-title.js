import {LitElement, html, css} from 'lit';
import '@vaadin/icon';
import '@vaadin/button';
import '@vaadin/text-field';
import '@vaadin/text-area';
import '@vaadin/form-layout';
import '@vaadin/progress-bar';
import '@vaadin/checkbox';
import '@vaadin/grid';
import '@vaadin/grid/vaadin-grid-sort-column.js';

export class DemoTitle extends LitElement {

    static styles = css`
      h1 {
        font-family: "Red Hat Mono", monospace;
        font-size: 60px;
        font-style: normal;
        font-variant: normal;
        font-weight: 700;
        line-height: 26.4px;
        color: var(--main-highlight-text-color);
      }

      .title {
        text-align: center;
        padding: 1em;
        background: var(--main-bg-color);
      }
      
      .explanation {
        margin-left: auto;
        margin-right: auto;
        width: 50%;
        text-align: justify;
        font-size: 20px;
      }
    `

    render() {
        return html`
            <div class="title">
                <h1>Portal de comunicações</h1>
            </div>
            <div class="explanation">
                Esta demo mostra como uma LLM pode fazer uma triagem inicial automaticamente.
                Escreva algo (reclamação, sugestão, elogio) sobre algum dos nossos serviço e submeta.
                Utilizamos uma LLM (mistral:7b-instruct-q4_K_M) para analizar a requisição e prover uma resposta adequada.
            </div>
        `
    }


}

customElements.define('demo-title', DemoTitle);