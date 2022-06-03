import React, { Component } from 'react';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import NavBar from "./NavBar";

class UrlShorten extends Component {

    constructor(props) {
        super(props);
        this.state = {
            longUrl: '',
            shortUrl: '',
            message: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState( {longUrl: event.target.value});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {longUrl, shortUrl} = this.state;

        await fetch('/v1/url/shortifyUrl', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({longUrl, shortUrl}),
        }).then(response => response.json())
        .then(data => this.setState({...data, message: data.message}));
    }

    render() {
        const {longUrl, shortUrl, message} = this.state;

        return <div>
            <NavBar/>
            <Container>
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="longUrl">Url</Label>
                        <Input type="text" name="longUrl" id="longUrl" value={longUrl || ''}
                               onChange={this.handleChange} autoComplete="longUrl"/>
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="submit">Shorten</Button>
                    </FormGroup>
                    <FormGroup>
                        {
                            shortUrl && (
                                <div>
                                    <Label for={shortUrl}>Result</Label>
                                    <div><a href={longUrl} id="shortUrl" >{shortUrl}</a></div>
                                </div>
                            )
                        }
                    </FormGroup>
                    <FormGroup>
                        {
                            message && (
                                <div>
                                    <Label style={{color: "red"}}>{message}</Label>
                                </div>
                            )
                        }
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default UrlShorten;